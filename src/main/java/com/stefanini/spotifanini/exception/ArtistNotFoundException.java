package com.stefanini.spotifanini.exception;

public class ArtistNotFoundException extends Exception {

    public ArtistNotFoundException(Long id) {
        super("Artist Not Found With ID: " + id);
    }
}
