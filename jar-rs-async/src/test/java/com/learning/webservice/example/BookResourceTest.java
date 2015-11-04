/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.learning.webservice.example;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.learning.webservice.example.repository.BookDao;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class BookResourceTest extends JerseyTest {

    private String book1_id;
    private String book2_id;

    @Override
    protected javax.ws.rs.core.Application configure() {

//        enable(TestProperties.LOG_TRAFFIC);
//        enable(TestProperties.DUMP_ENTITY);

        final BookDao bookDao = new BookDao();
        return new Application(bookDao);
    }

    @Override
    protected void configureClient(ClientConfig clientConfig){
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        clientConfig.register(provider);
        clientConfig.connectorProvider(new GrizzlyConnectorProvider());
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

    @Test
    public void testAddBookNoAuthor(){
        Response response = addBook(null, "Title2", new Date(), "1234");
        assertEquals(400 ,response.getStatus());
        assertTrue(response.readEntity(String.class).contains("author is required."));
    }

    @Test
    public void testAddBookNoTitle(){
        Response response = addBook("Ming", null, new Date(), "1234");
        assertEquals(400 ,response.getStatus());
        assertTrue(response.readEntity(String.class).contains("title is required."));
    }

    @Test
    public void testAddNoBook(){
        Response response = target("books").request().post(null);
        assertEquals(400 ,response.getStatus());
    }

    @Test
    public void testBookNotFound(){
        Response response = target("books").path("1").request().get();
        assertEquals(404, response.getStatus());

        String message = response.readEntity(String.class);
        assertEquals("Book not found: 1", message);
    }

    @Test
    public void testBookEntityTagNotModified(){
        EntityTag entityTag = target("books").path(book1_id).request().get().getEntityTag();
        assertNotNull(entityTag);

        Response response = target("books").path(book1_id).request().header("If-None-Match", entityTag).get();
        assertEquals(304, response.getStatus());
    }

    @Test
    public void testUpdateBookAuthor(){
        HashMap<String, Object> book = new HashMap<>();
        book.put("author", "Awesome");
        Response response = target("books").path(book1_id)
                .request().build("PATCH", Entity.entity(book, MediaType.APPLICATION_JSON_TYPE)).invoke();
        assertEquals(200, response.getStatus());

        Response getResponse = target("books").path(book1_id).request().get();
        HashMap<String, Object> getRespMap = toHashMap(getResponse);
        assertEquals("Awesome", getRespMap.get("author"));
    }

    @Test
    public void testUpdateBookExtras(){
        HashMap<String, Object> book = new HashMap<>();
        book.put("hello", "world");
        Response response = target("books").path(book1_id)
                .request().build("PATCH", Entity.entity(book, MediaType.APPLICATION_JSON_TYPE)).invoke();
        assertEquals(200, response.getStatus());

        Response getResponse = target("books").path(book1_id).request().get();
        HashMap<String, Object> getRespMap = toHashMap(getResponse);
        assertEquals("world", getRespMap.get("hello"));
    }


    @Test
    public void testUpdateIfMatch(){
        EntityTag entityTag = target("books").path(book1_id).request().get().getEntityTag();

        HashMap<String, Object> updates = new HashMap<>();
        updates.put("author", "updated author");
        Entity<HashMap<String, Object>> updateEntity = Entity.entity(updates, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("books").path(book1_id).request().header("If-Match", entityTag).build("PATCH", updateEntity).invoke();
        assertEquals(200, response.getStatus());

        Response response2 = target("books").path(book1_id).request().header("If-Match", entityTag).build("PATCH", updateEntity).invoke();
        assertEquals(412, response2.getStatus());

        Response response3 = target("books").path(book1_id).request().header("If-Match", response.getEntityTag()).build("PATCH", updateEntity).invoke();
        assertEquals(200, response3.getStatus());
    }

    @Test
    public void testPatchOverride(){
        HashMap<String, Object> book = new HashMap<>();
        book.put("author", "Awesome");
        Response response = target("books").path(book1_id).queryParam("_method", "PATCH")
                .request().post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(200, response.getStatus());

        Response getResponse = target("books").path(book1_id).request().get();
        HashMap<String, Object> getRespMap = toHashMap(getResponse);
        assertEquals("Awesome", getRespMap.get("author"));
    }

    @Test
    public void testContentNegiotation(){
        Response xmlResponse = target("books").path(book1_id + ".xml").request().get();
        assertEquals(MediaType.APPLICATION_XML, xmlResponse.getHeaderString("Content-Type"));

        Response jsonResponse = target("books").path(book1_id + ".json").request().get();
        assertEquals(MediaType.APPLICATION_JSON, jsonResponse.getHeaderString("Content-Type"));
    }

    @Test
    public void testPoweredByFilter(){
        Response xmlResponse = target("books").path(book1_id + ".xml").request().get();
        assertEquals("Ming", xmlResponse.getHeaderString("X-Powered-By"));
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