/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.learning.webservice.example;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class BookResourceTest extends JerseyTest {

    private String book1_id;
    private String book2_id;

    @Override
    protected Application configure() {

        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        final BookDao bookDao = new BookDao();
        return new BookApplication(bookDao);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        book1_id = (String) toHashMap(addBook("Author 1", "Title 1", new Date(), "1234")).get("id");
        book2_id = (String) toHashMap(addBook("Author 2", "Title 2", new Date(), "2345")).get("id");
    }

    @Test
    public void testGetBook() {
        HashMap<String, Object> response = toHashMap(target("books").path(book1_id).request().get());
        assertNotNull(response);
        assertEquals("Title 1", response.get("title"));
        assertEquals("Author 1", response.get("author"));
    }

    @Test
    public void testGetBooks() {
        Collection<HashMap<String, Object>> response = target("books").request().get(new GenericType<Collection<HashMap<String, Object>>>(){});
        assertNotNull(response);
        assertEquals(3, response.size());
    }

    @Test
    public void testAddBook() throws ParseException {
        Date thisDate = new Date();
        Response response = addBook("Ming", "Hell World", thisDate, "123456");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        assertEquals(200, response.getStatus());
        HashMap<String, Object> responseBook = toHashMap(response);
        assertEquals("Hell World", responseBook.get("title"));
        assertEquals("Ming", responseBook.get("author"));
        assertEquals("123456", responseBook.get("isbn"));
        assertEquals(thisDate, dateFormat.parse((String) responseBook.get("published")));

    }

    @Test
    public void testAddExtraField(){
        Response response = addBook("author", "title", new Date(), "9999", "hello china");
        assertEquals(200, response.getStatus());

        HashMap<String, Object> book = toHashMap(response);
        assertNotNull(book.get("id"));
        assertNotNull("hello china", book.get("extra1"));
    }

    @Test
    public void testGetBookAsString(){
        String output = target("books").request(MediaType.APPLICATION_XML_TYPE).get().readEntity(String.class);
        XML xml = new XMLDocument(output);
        assertEquals("Author 1", xml.xpath("/books/book[@id='" + book1_id + "']/author/text()").get(0));
        assertEquals("Title 2", xml.xpath("/books/book[@id='" + book2_id + "']/title/text()").get(0));
        assertEquals(3, xml.xpath("//books/book/author/text()").size());
    }

    protected Response addBook(String author, String title, Date published, String isbn, String... args){
        HashMap<String, Object> book = new HashMap<>();
        book.put("author", author);
        book.put("title", title);
        book.put("published", published);
        book.put("isbn", isbn);
        if(args != null){
            int count = 1;
            for(String s : args){
                book.put("extra"+count++, s);
            }
        }

        return target("books").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));
    }

    protected HashMap<String, Object> toHashMap(Response response){
        return response.readEntity(new GenericType<HashMap<String, Object>>(){});
    }
}