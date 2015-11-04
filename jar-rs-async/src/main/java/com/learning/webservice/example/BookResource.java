package com.learning.webservice.example;

import jersey.repackaged.com.google.common.util.concurrent.FutureCallback;
import jersey.repackaged.com.google.common.util.concurrent.Futures;
import jersey.repackaged.com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.codec.digest.DigestUtils;
import org.glassfish.jersey.server.ManagedAsync;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.util.Collection;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
@Path(("books"))
public class BookResource {

    @Context
    private Request request;

    @Context
    private BookDao bookDao;

/*    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Book> getBooks(){
        return bookDao.getBooks();
    }*/

    @Path("/{id}")
    @GET
    @Produces({"application/json;qs=1", "application/xml;qs=0.5"})
    @ManagedAsync
    public void getBook(@PathParam("id") String id, @Suspended AsyncResponse response) {
        ListenableFuture<Book> bookFuture = bookDao.getBookAsync(id);
        Futures.addCallback(bookFuture, new FutureCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                EntityTag entityTag = generateEntityTag(result);
                Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);
                if(builder != null){
                    response.resume(builder.build());
                } else{
                    response.resume(Response.ok().tag(entityTag).entity(result).build());
                }

            }

            @Override
            public void onFailure(Throwable t) {
                response.resume(t);
            }
        });
    }

    @GET
    @Produces({"application/json;qs=1", "application/xml;qs=0.5"})
    @ManagedAsync
    public void getBooks(@Suspended AsyncResponse response) {
        ListenableFuture<Collection<Book>> bookFuture = bookDao.getBooksAsync();
        Futures.addCallback(bookFuture, new FutureCallback<Collection<Book>>() {
            @Override
            public void onSuccess(Collection<Book> result) {
                response.resume(result);
            }

            @Override
            public void onFailure(Throwable t) {
                response.resume(t);
            }
        });
    }

    @POST
    @Produces({"application/json;qs=1", "application/xml;qs=0.5"})
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void addBook(@Valid @NotNull Book book, @Suspended AsyncResponse response) {
        ListenableFuture<Book> bookFuture = bookDao.addBookAsync(book);
        Futures.addCallback(bookFuture, new FutureCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                response.resume(result);
            }

            @Override
            public void onFailure(Throwable t) {
                response.resume(t);
            }
        });
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    @Produces({"application/json;qs=1", "application/xml;qs=0.5"})
    public void updateBook(@PathParam("id") String id, Book book, @Suspended AsyncResponse response){
        ListenableFuture<Book> bookFuture = bookDao.updateAsync(id, book);
        Futures.addCallback(bookFuture, new FutureCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                response.resume(result);
            }

            @Override
            public void onFailure(Throwable t) {
                response.resume(t);
            }
        });
    }

    private EntityTag generateEntityTag(final Book book) {
        return new EntityTag(DigestUtils.md2Hex(book.getAuthor() + book.getTitle() + book.getPublished() + book.getIsbn() + book.getExtras()));
    }
}
