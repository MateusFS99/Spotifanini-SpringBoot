package com.stefanini.spotifanini.exception;

public class GenreNotFoundException extends Exception {
    
    public GenreNotFoundException(Long id) {
        super("Genre Not Found With ID: " + id);
    }
}
