package com.stefanini.spotifanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.exception.MusicNotFoundException;
import com.stefanini.spotifanini.exception.PlaylistNotFoundException;
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

    // <---------- GET All Playlists ---------->
    @ApiOperation(value = "Playlist List", notes = "This Endpoint Provides The List of All Playlists")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Playlists Returned")
    })
    @GetMapping
    public List<Playlist> findAllPlaylists() {
        return playlistService.findAllPlaylists();
    }

    // <---------- GET Playlist ---------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Provides The Playlist by The ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Returned")
    })
    @GetMapping("/{id}")
    public Playlist findById(@PathVariable Long id) throws PlaylistNotFoundException {
        return playlistService.findById(id);
    }

    // <---------- POST METHODS ---------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Saves a New Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Saved")
    })
    @PostMapping
    public Playlist save(@RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    // Add Music to Playlist
    @ApiOperation(value = "Playlist Music", notes = "This Endpoint Add a Music in a Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Added to Playlist")
    })
    @PostMapping("/{playlistId}/music/{musicId}")
    public Playlist addMusic(@PathVariable Long playlistId, @PathVariable Long musicId)
            throws PlaylistNotFoundException, MusicNotFoundException {
        return playlistService.addMusic(playlistId, musicId);
    }

    // <---------- PUT METHOD ---------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Updates a Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Updated")
    })
    @PutMapping("/{id}")
    public Playlist update(@PathVariable Long id, @RequestBody Playlist playlist) throws PlaylistNotFoundException {
        return playlistService.update(id, playlist);
    }

    // <---------- DELETE METHOD ---------->
    @ApiOperation(value = "Playlist", notes = "This Endpoint Deletes a Playlist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Playlist Deleted")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws PlaylistNotFoundException {
        playlistService.delete(id);
    }
}
