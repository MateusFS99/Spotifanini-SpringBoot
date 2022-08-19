package com.stefanini.spotifanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.exception.MusicNotFoundException;
import com.stefanini.spotifanini.model.Music;
import com.stefanini.spotifanini.service.MusicService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @ApiOperation(value = "Music List", notes = "This Endpoint Provides The List of All Musics")
    @ApiResponses({
            @ApiResponse(code = 204, message = "List Returned")
    })
    @GetMapping
    public List<Music> findAllMusics() {
        return musicService.findAllMusics();
    }

    @ApiOperation(value = "Music", notes = "This Endpoint Provides The Music by The ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Music Returned")
    })
    @GetMapping("/{id}")
    public Music findById(@PathVariable Long id) throws MusicNotFoundException {
        return musicService.findById(id);
    }

    @ApiOperation(value = "Music", notes = "This Endpoint Saves an Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Saved")
    })
    @PostMapping
    public Music save(@RequestBody Music music) {
        return musicService.save(music);
    }

    @ApiOperation(value = "Music", notes = "This Endpoint Updates an Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Updated")
    })
    @PutMapping("/{id}")
    public Music update(@PathVariable Long id, @RequestBody Music music) throws MusicNotFoundException {
        return musicService.update(id, music);
    }

    @ApiOperation(value = "Music", notes = "This Endpoint Deletes an Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Deleted")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws MusicNotFoundException {
        musicService.delete(id);
    }
}
