package com.stefanini.spotifanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.exception.ArtistNotFoundException;
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

    //<---------- GET All Artists ---------->
    @ApiOperation(value = "Artist List", notes = "This Endpoint Provides The List of All Artists")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Artists Returned")
    })
    @GetMapping
    public List<Artist> findAllArtists() {
        return artistService.findAllArtists();
    }

    //<---------- GET Artist ---------->
    @ApiOperation(value = "Artist", notes = "This Endpoint Provides The Artist by The ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Returned")
    })
    @GetMapping("/{id}")
    public Artist findById(@PathVariable Long id) throws ArtistNotFoundException {
        return artistService.findById(id);
    }

    //<---------- POST METHOD ---------->
    @ApiOperation(value = "Artist", notes = "This Endpoint Saves an Artist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Saved")
    })
    @PostMapping
    public Artist save(@RequestBody Artist artist) {
        return artistService.save(artist);
    }

    //<---------- PUT METHOD ---------->
    @ApiOperation(value = "Artist", notes = "This Endpoint Updates an Artist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Updated")
    })
    @PutMapping("/{id}")
    public Artist update(@PathVariable Long id, @RequestBody Artist artist) throws ArtistNotFoundException {
        return artistService.update(id, artist);
    }

    //<---------- DELETE METHOD ---------->
    @ApiOperation(value = "Artist", notes = "This Endpoint Deletes an Artist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Artist Deleted")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws ArtistNotFoundException {
        artistService.delete(id);
    }
}
