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

import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.service.MusicService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    // <-------------------- GET All Musics -------------------->
    @ApiOperation(value = "Music List", notes = "This Endpoint Provides The List of All Musics")
    @GetMapping
    public List<Music> findAllMusics() {
        return musicService.findAllMusics();
    }

    // <-------------------GET Music -------------------->
    @ApiOperation(value = "Music", notes = "This Endpoint Provides The Music by The ID")
    @GetMapping("/{id}")
    public Music findById(@PathVariable Long id) {
        return musicService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @ApiOperation(value = "Music", notes = "This Endpoint Saves a New Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Saved"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Music Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Music music) {
        return musicService.save(music);
    }

    // Add Participant to Music
    @ApiOperation(value = "Music Participant", notes = "This Endpoint Adds a Participant Artist in a Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Participant Added to the Music"),
            @ApiResponse(code = 500, message = "Server Side Exception"),
            @ApiResponse(code = 400, message = "This Artist is The Author of The Album"),
            @ApiResponse(code = 401, message = "Participant Already Added"),
            @ApiResponse(code = 404, message = "Music Not Found"),
            @ApiResponse(code = 405, message = "Participant Not Found")
    })
    @PostMapping("/{musicId}/addParticipant/{artistId}")
    public ResponseEntity<String> addParticipant(@PathVariable Long musicId, @PathVariable Long artistId) {
        return musicService.addParticipant(musicId, artistId);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Music", notes = "This Endpoint Updates a Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Updated"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Music Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Music music) {
        return musicService.update(id, music);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Music", notes = "This Endpoint Deletes a Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return musicService.delete(id);
    }

    // Remove Participant of Music
    @ApiOperation(value = "Music Participant", notes = "This Endpoint Removes a Participant Artist of a Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Removed of the Playlist"),
            @ApiResponse(code = 500, message = "Server Side Exception"),
            @ApiResponse(code = 400, message = "The Author of The Album Cannot Be Removed"),
            @ApiResponse(code = 401, message = "Participant Doesn't Exists in The Music"),
            @ApiResponse(code = 404, message = "Music Not Found"),
            @ApiResponse(code = 405, message = "Participant Not Found")
    })
    @DeleteMapping("/{musicId}/removeParticipant/{artistId}")
    public ResponseEntity<String> removeParticipant(@PathVariable Long musicId, @PathVariable Long artistId) {
        return musicService.removeParticipant(musicId, artistId);
    }
}
