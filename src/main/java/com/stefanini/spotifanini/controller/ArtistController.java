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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // <-------------------- GET All Artists -------------------->
    @Operation(summary = "Get All Artists", description = "This Endpoint Provides The List of All Artists With Pagination")
    @GetMapping
    public Page<Artist> findAllArtists(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return artistService.findAllArtists(pagination);
    }

    // <-------------------- GET Artist -------------------->
    @Operation(summary = "Get Artist", description = "This Endpoint Provides The Artist by The ID")
    @GetMapping("/{id}")
    public Artist findById(@PathVariable Long id) {
        return artistService.findById(id);
    }

    // <------------------- POST METHOD -------------------->
    @Operation(summary = "Save Artist", description = "This Endpoint Saves an New Artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist Saved"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Artist Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Artist artist) {
        return artistService.save(artist);
    }

    // <-------------------- PUT METHOD -------------------->
    @Operation(summary = "Update Artist", description = "This Endpoint Updates an Artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist Updated"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Artist Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Artist artist) {
        return artistService.update(id, artist);
    }

    // <-------------------- DELETE METHOD -------------------->
    @Operation(summary = "Delete Artist", description = "This Endpoint Deletes an Artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist Deleted"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return artistService.delete(id);
    }
}
