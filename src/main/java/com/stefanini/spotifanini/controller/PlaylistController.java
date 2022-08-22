package com.stefanini.spotifanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.model.Playlist;
import com.stefanini.spotifanini.service.PlaylistService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // <-------------------- GET All Playlists -------------------->
    @ApiOperation(value = "Playlist List", notes = "This Endpoint Provides The List of All Playlists")
    @GetMapping
    public List<Playlist> findAllPlaylists() {
        return playlistService.findAllPlaylists();
    }

    // <-------------------- GET Playlist -------------------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Provides The Playlist by The ID")
    @GetMapping("/{id}")
    public Playlist findById(@PathVariable Long id) {
        return playlistService.findById(id);
    }

    // <------------------- POST METHODS -------------------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Saves a New Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Saved"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Playlist Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    // Add Music to Playlist
    @ApiOperation(value = "Playlist Music", notes = "This Endpoint Adds a Music in a Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Added to the Playlist"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping("/{playlistId}/music/{musicId}")
    public ResponseEntity<String> addMusic(@PathVariable Long playlistId, @PathVariable Long musicId) {
        return playlistService.addMusic(playlistId, musicId);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Updates a Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Updated"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Playlist Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Playlist playlist) {
        return playlistService.update(id, playlist);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Deletes a Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return playlistService.delete(id);
    }
}
