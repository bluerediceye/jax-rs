/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.learning.webservice.example;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

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
        book1_id = addBook("Author 1", "Title 1", new Date(), "1234").readEntity(Book.class).getId();
        book2_id = addBook("Author 2", "Title 2", new Date(), "2345").readEntity(Book.class).getId();
    }

    @Test
    public void testGetBook() {
        Book response = target("books").path(book1_id).request().get(Book.class);

        assertNotNull(response);
        assertEquals("Title 1", response.getTitle());
        assertEquals("Author 1", response.getAuthor());
    }

    @Test
    public void testGetBooks() {
        Collection<Book> books = target("books").request().get(new GenericType<Collection<Book>>() {
        });
        assertNotNull(books);
        assertEquals(2, books.size());
    }

    @Test
    public void testAddBook(){
        Response response = addBook("Ming", "Hell World", new Date(), "123456");

        assertEquals(200, response.getStatus());
        Book responseBook = response.readEntity(Book.class);
        assertEquals("Hell World", responseBook.getTitle());
        assertEquals("Ming", responseBook.getAuthor());
        assertEquals("123456", responseBook.getIsbn());

    }

    protected Response addBook(String author, String title, Date published, String isbn){
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setPublished(published);
        book.setIsbn(isbn);
        return target("books").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));
    }
}
