package com.stefanini.spotifanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.exception.ArtistNotFoundException;
import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.service.ArtistService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @ApiOperation(value = "Artist List", notes = "This Endpoint Provides The List of All Artists")
    @ApiResponses({
            @ApiResponse(code = 204, message = "List Returned")
    })
    @GetMapping
    public List<Artist> findAllArtists() {
        return artistService.findAllArtists();
    }

    @ApiOperation(value = "Artist", notes = "This Endpoint Provides The Artist by The ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Artist Returned")
    })
    @GetMapping("/{id}")
    public Artist findById(@PathVariable Long id) throws ArtistNotFoundException {
        return artistService.findById(id);
    }

    @PostMapping
    public Artist save(@RequestBody Artist artist) {
        return artistService.save(artist);
    }
}
