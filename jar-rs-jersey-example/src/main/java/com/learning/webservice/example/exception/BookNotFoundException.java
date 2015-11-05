package com.learning.webservice.example.exception;

/**
 * Created by Ming.Li on 04/11/2015.
 *
 * @author Ming.Li
 */
public class BookNotFoundException extends  Exception{
    public BookNotFoundException(String msg) {
        super(msg);
    }
}
