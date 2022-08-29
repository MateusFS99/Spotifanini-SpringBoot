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
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiOperation(value = "Get All Musics", notes = "This Endpoint Provides The List of All Musics With Pagination")
    @GetMapping
    public Page<Music> findAllMusics(@RequestParam int page,
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return musicService.findAllMusics(pagination);
    }

    // <-------------------GET Music -------------------->
    @ApiOperation(value = "Get Music", notes = "This Endpoint Provides The Music by The ID")
    @GetMapping("/{id}")
    public Music findById(@PathVariable Long id) {
        return musicService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @ApiOperation(value = "Save Music", notes = "This Endpoint Saves a New Music")
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

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Update Music", notes = "This Endpoint Updates a Music")
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
    @ApiOperation(value = "Delete Music", notes = "This Endpoint Deletes a Music")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return musicService.delete(id);
    }
}
