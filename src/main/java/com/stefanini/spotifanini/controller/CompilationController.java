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

import com.stefanini.spotifanini.model.Compilation;
import com.stefanini.spotifanini.service.CompilationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/compilation")
public class CompilationController {

    @Autowired
    private final CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    // <-------------------- GET All Compilations -------------------->
    @ApiOperation(value = "Compilation List", notes = "This Endpoint Provides The List of All Compilations")
    @GetMapping
    public List<Compilation> findAllCompilations() {
        return compilationService.findAllCompilations();
    }

    // <-------------------GET Compilation -------------------->
    @ApiOperation(value = "Compilation", notes = "This Endpoint Provides The Compilation by The ID")
    @GetMapping("/{id}")
    public Compilation findById(@PathVariable Long id) {
        return compilationService.findById(id);
    }

    // <-------------------- POST METHOD -------------------->
    @ApiOperation(value = "Compilation", notes = "This Endpoint Saves a New Compilation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compilation Saved"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Compilation Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Compilation compilation) {
        return compilationService.save(compilation);
    }

    // Add Music to Compilation
    @ApiOperation(value = "Compilation Music", notes = "This Endpoint Adds a Music in a Compilation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Music Added to the Compilation"),
            @ApiResponse(code = 500, message = "Server Side Exception"),
            @ApiResponse(code = 400, message = "Music Already Added"),
            @ApiResponse(code = 404, message = "Compilation Not Found"),
            @ApiResponse(code = 405, message = "Music Not Found")
    })
    @PostMapping("/{compilationId}/addMusic/{musictId}")
    public ResponseEntity<String> addMusic(@PathVariable Long compilationId, @PathVariable Long musictId) {
        return compilationService.addMusic(compilationId, musictId);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Compilation", notes = "This Endpoint Updates a Compilation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compilation Updated"),
            @ApiResponse(code = 400, message = "Empty Name"),
            @ApiResponse(code = 401, message = "Compilation Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Compilation compilation) {
        return compilationService.update(id, compilation);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Compilation", notes = "This Endpoint Deletes a Compilation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compilation Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return compilationService.delete(id);
    }

    // Remove Music of Compilation
    @ApiOperation(value = "Compilation Music", notes = "This Endpoint Removes a Music of a Compilation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Compilation Removed of the Playlist"),
            @ApiResponse(code = 500, message = "Server Side Exception"),
            @ApiResponse(code = 400, message = "Music Doesn't Exists in The Compilation"),
            @ApiResponse(code = 404, message = "Compilation Not Found"),
            @ApiResponse(code = 405, message = "Music Not Found")
    })
    @DeleteMapping("/{compilationId}/removeMusic/{musictId}")
    public ResponseEntity<String> removeMusic(@PathVariable Long compilationId, @PathVariable Long musictId) {
        return compilationService.removeMusic(compilationId, musictId);
    }
}
