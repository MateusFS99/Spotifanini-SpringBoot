package com.stefanini.spotifanini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.exception.GenreNotFoundException;
import com.stefanini.spotifanini.model.Genre;
import com.stefanini.spotifanini.service.GenreService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @ApiOperation(value = "Genre List", notes = "This Endpoint Provides The List of All Genres")
    @ApiResponses({
            @ApiResponse(code = 204, message = "List Returned")
    })
    @GetMapping
    public List<Genre> findAllGenres() {
        return genreService.findAllGenres();
    }

    @ApiOperation(value = "Genre", notes = "This Endpoint Provides The Genre by The ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Genre Returned")
    })
    @GetMapping("/{id}")
    public Genre findById(@PathVariable Long id) throws GenreNotFoundException {
        return genreService.findById(id);
    }

    @PostMapping
    public Genre save(@RequestBody Genre genre) {
        return genreService.save(genre);
    }
}
