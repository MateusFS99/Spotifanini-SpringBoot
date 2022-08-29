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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // <-------------------- GET All Genres -------------------->
    @ApiOperation(value = "Get All Genres", notes = "This Endpoint Provides The List of All Genres With Pagination")
    @GetMapping
    @Cacheable(value = "genresList")
    public Page<Genre> findAllGenres(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {
        return genreService.findAllGenres(pagination);
    }

    // <-------------------- GET Genre -------------------->
    @ApiOperation(value = "Get Genre", notes = "This Endpoint Provides The Genre by The ID")
    @GetMapping("/{id}")
    @Cacheable(value = "genresList")
    public Genre findById(@PathVariable Long id) {
        return genreService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @ApiOperation(value = "Save Genre", notes = "This Endpoint Saves a New Genre")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Genre Saved"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Genre Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    @CacheEvict(value = "genresList", allEntries = true)
    public ResponseEntity<String> save(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Update Genre", notes = "This Endpoint Updates a Genre")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Genre Updated"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Genre Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    @CacheEvict(value = "genresList", allEntries = true)
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Genre genre) {
        return genreService.update(id, genre);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Delete Genre", notes = "This Endpoint Deletes a Genre")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Genre Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    @CacheEvict(value = "genresList", allEntries = true)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return genreService.delete(id);
    }
}
