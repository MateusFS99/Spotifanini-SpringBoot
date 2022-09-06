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

import com.stefanini.spotifanini.model.Album;
import com.stefanini.spotifanini.service.AlbumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // <-------------------- GET All Albums -------------------->
    @Operation(summary = "Get All Albuns", description = "This Endpoint Provides The List of All Albums With Pagination")
    @GetMapping
    public Page<Album> findAllAlbums(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return albumService.findAllAlbums(pagination);
    }

    // <-------------------GET Album -------------------->
    @Operation(summary = "Get Album", description = "This Endpoint Provides The Album by The ID")
    @GetMapping("/{id}")
    public Album findById(@PathVariable Long id) {
        return albumService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @Operation(summary = "Save Album", description = "This Endpoint Saves an New Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album Saved"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Album Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Album album) {
        return albumService.save(album);
    }

    // <-------------------- PUT METHOD -------------------->
    @Operation(summary = "Update Album", description = "This Endpoint Updates an Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album Updated"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Album Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Album album) {
        return albumService.update(id, album);
    }

    // <-------------------- DELETE METHOD -------------------->
    @Operation(summary = "Delete Album", description = "This Endpoint Deletes an Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album Deleted"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return albumService.delete(id);
    }
}
