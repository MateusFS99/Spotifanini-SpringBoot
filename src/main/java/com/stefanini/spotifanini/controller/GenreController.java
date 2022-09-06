package com.stefanini.spotifanini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import com.stefanini.spotifanini.model.Genre;
import com.stefanini.spotifanini.service.GenreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // <-------------------- GET All Genres -------------------->
    @Operation(summary = "Get All Genres", description = "This Endpoint Provides The List of All Genres With Pagination")
    @GetMapping
    @Cacheable(value = "genresList")
    public Page<Genre> findAllGenres(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return genreService.findAllGenres(pagination);
    }

    // <-------------------- GET Genre -------------------->
    @Operation(summary = "Get Genre", description = "This Endpoint Provides The Genre by The ID")
    @GetMapping("/{id}")
    @Cacheable(value = "genresList")
    public Genre findById(@PathVariable Long id) {
        return genreService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @Operation(summary = "Save Genre", description = "This Endpoint Saves a New Genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre Saved"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Genre Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PostMapping
    @CacheEvict(value = "genresList", allEntries = true)
    public ResponseEntity<String> save(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    // <-------------------- PUT METHOD -------------------->
    @Operation(summary = "Update Genre", description = "This Endpoint Updates a Genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre Updated"),
            @ApiResponse(responseCode = "400", description = "Empty Name"),
            @ApiResponse(responseCode = "401", description = "Genre Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PutMapping("/{id}")
    @CacheEvict(value = "genresList", allEntries = true)
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Genre genre) {
        return genreService.update(id, genre);
    }

    // <-------------------- DELETE METHOD -------------------->
    @Operation(summary = "Delete Genre", description = "This Endpoint Deletes a Genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre Deleted"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    @CacheEvict(value = "genresList", allEntries = true)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return genreService.delete(id);
    }
}
