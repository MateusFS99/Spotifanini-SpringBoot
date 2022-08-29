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

import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.service.ArtistService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // <-------------------- GET All Artists -------------------->
    @ApiOperation(value = "Get All Artists", notes = "This Endpoint Provides The List of All Artists With Pagination")
    @GetMapping
    public Page<Artist> findAllArtists(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return artistService.findAllArtists(pagination);
    }

    // <-------------------- GET Artist -------------------->
    @ApiOperation(value = "Get Artist", notes = "This Endpoint Provides The Artist by The ID")
    @GetMapping("/{id}")
    public Artist findById(@PathVariable Long id) {
        return artistService.findById(id);
    }

    // <------------------- POST METHOD -------------------->
    @ApiOperation(value = "Save Artist", notes = "This Endpoint Saves an New Artist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Saved"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Artist Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Artist artist) {
        return artistService.save(artist);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Update Artist", notes = "This Endpoint Updates an Artist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Updated"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Artist Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Artist artist) {
        return artistService.update(id, artist);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Delete Artist", notes = "This Endpoint Deletes an Artist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return artistService.delete(id);
    }
}
