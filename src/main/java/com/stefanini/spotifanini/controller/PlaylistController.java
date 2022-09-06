package com.stefanini.spotifanini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // <-------------------- GET All Playlists -------------------->
    @Operation(summary = "Get All Playlists", description = "This Endpoint Provides The List of All Playlists With Pagination")
    @GetMapping
    public Page<Playlist> findAllPlaylists(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return playlistService.findAllPlaylists(pagination);
    }

    // <-------------------- GET Playlist -------------------->
    @Operation(summary = "Get Playlist", description = "This Endpoint Provides The Playlist by The ID")
    @GetMapping("/{id}")
    public Playlist findById(@PathVariable Long id) {
        return playlistService.findById(id);
    }

    // <------------------- POST METHODS -------------------->
    @Operation(summary = "Save Playlist", description = "This Endpoint Saves a New Playlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playlist Saved"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Playlist Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    // Add Music to Playlist
    @Operation(summary = "Add Music to Playlist", description = "This Endpoint Adds a Music in a Playlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Music Added to the Playlist"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception"),
            @ApiResponse(responseCode = "400", description = "Music Already Added"),
            @ApiResponse(responseCode = "404", description = "Playlist Not Found"),
            @ApiResponse(responseCode = "405", description = "Music Not Found")
    })
    @PostMapping("/{playlistId}/addMusic/{musicId}")
    public ResponseEntity<String> addMusic(@PathVariable Long playlistId, @PathVariable Long musicId) {
        return playlistService.addMusic(playlistId, musicId);
    }

    // <-------------------- PUT METHOD -------------------->
    @Operation(summary = "Update Playlist", description = "This Endpoint Updates a Playlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playlist Updated"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Playlist Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Playlist playlist) {
        return playlistService.update(id, playlist);
    }

    // <-------------------- DELETE METHOD -------------------->
    @Operation(summary = "Delete Playlist", description = "This Endpoint Deletes a Playlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playlist Deleted"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return playlistService.delete(id);
    }

    // Remove Music of Playlist
    @Operation(summary = "Remove Music from Playlist", description = "This Endpoint Removes a Music from a Playlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Music Removed to the Playlist"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception"),
            @ApiResponse(responseCode = "400", description = "Music Doesn't Exists in The Playlist"),
            @ApiResponse(responseCode = "404", description = "Playlist Not Found"),
            @ApiResponse(responseCode = "405", description = "Music Not Found")
    })
    @DeleteMapping("/{playlistId}/removeMusic/{musicId}")
    public ResponseEntity<String> removeMusic(@PathVariable Long playlistId, @PathVariable Long musicId) {
        return playlistService.removeMusic(playlistId, musicId);
    }
}
