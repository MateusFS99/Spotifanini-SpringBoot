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

import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.service.MusicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    // <-------------------- GET All Musics -------------------->
    @Operation(summary = "Get All Musics", description = "This Endpoint Provides The List of All Musics With Pagination")
    @GetMapping
    public Page<Music> findAllMusics(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return musicService.findAllMusics(pagination);
    }

    // <-------------------GET Music -------------------->
    @Operation(summary = "Get Music", description = "This Endpoint Provides The Music by The ID")
    @GetMapping("/{id}")
    public Music findById(@PathVariable Long id) {
        return musicService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @Operation(summary = "Save Music", description = "This Endpoint Saves a New Music")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music Saved"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Music Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Music music) {
        return musicService.save(music);
    }

    // <-------------------- PUT METHOD -------------------->
    @Operation(summary = "Update Music", description = "This Endpoint Updates a Music")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music Updated"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Music Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Music music) {
        return musicService.update(id, music);
    }

    // <-------------------- DELETE METHOD -------------------->
    @Operation(summary = "Delete Music", description = "This Endpoint Deletes a Music")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music Deleted"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return musicService.delete(id);
    }
}
