/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.learning.webservice.example;

import jersey.repackaged.com.google.common.util.concurrent.ListenableFuture;
import jersey.repackaged.com.google.common.util.concurrent.ListeningExecutorService;
import jersey.repackaged.com.google.common.util.concurrent.MoreExecutors;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class BookDao {

    private Map<String, Book> books;

    private ListeningExecutorService service;


    public BookDao(){
        books = new ConcurrentHashMap<>();

        Book testBook = new Book();
        testBook.setId("4");
        testBook.setAuthor("Ming");
        testBook.setIsbn("4234");
        testBook.setTitle("Hell");
        testBook.setPublished(new Date());
        books.put(testBook.getId(), testBook);

        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    public Collection<Book> getBooks(){
        return books.values();
    }

    public ListenableFuture<Collection<Book>> getBooksAsync(){
        return service.submit(this::getBooks);
    }

    public Book getBook(String id){
        return books.get(id);
    }

    public ListenableFuture<Book> getBookAsync(String id){
        return service.submit(() -> getBook(id));
    }

    public Book addBook(Book book){
        book.setId(UUID.randomUUID().toString());
        books.put(book.getId(), book);
        return book;
    }

    public ListenableFuture<Book> addBookAsync(final Book book){
        return service.submit(() -> addBook(book));
    }
}
