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

import com.stefanini.spotifanini.model.Album;
import com.stefanini.spotifanini.service.AlbumService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // <-------------------- GET All Albums -------------------->
    @ApiOperation(value = "Get All Albuns", notes = "This Endpoint Provides The List of All Albums")
    @GetMapping
    public List<Album> findAllAlbums() {
        return albumService.findAllAlbums();
    }

    // <-------------------GET Album -------------------->
    @ApiOperation(value = "Get Album", notes = "This Endpoint Provides The Album by The ID")
    @GetMapping("/{id}")
    public Album findById(@PathVariable Long id) {
        return albumService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @ApiOperation(value = "Save Album", notes = "This Endpoint Saves an New Album")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Album Saved"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Album Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Album album) {
        return albumService.save(album);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Update Album", notes = "This Endpoint Updates an Album")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Album Updated"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Album Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Album album) {
        return albumService.update(id, album);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Delete Album", notes = "This Endpoint Deletes an Album")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Album Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return albumService.delete(id);
    }
}
