package com.stefanini.spotifanini.exception;

public class PlaylistNotFoundException extends Exception {

    public PlaylistNotFoundException(Long id) {
        super("Playlist Not Found With ID: " + id);
    }
}
