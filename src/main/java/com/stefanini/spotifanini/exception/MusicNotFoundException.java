package com.stefanini.spotifanini.exception;

public class MusicNotFoundException extends Exception {

    public MusicNotFoundException(Long id) {
        super("Music Not Found With ID: " + id);
    }
}
