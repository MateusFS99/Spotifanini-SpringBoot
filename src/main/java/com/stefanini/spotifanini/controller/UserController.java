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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.spotifanini.model.User;
import com.stefanini.spotifanini.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // <-------------------- GET All Users -------------------->
    @Operation(summary = "Get All Users", description = "This Endpoint Provides The List of All Users With Pagination")
    @GetMapping
    public Page<User> findAllUsers(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {

        return userService.findAllUsers(pagination);
    }

    // <-------------------- GET User -------------------->
    @Operation(summary = "Get User", description = "This Endpoint Provides The User by The ID")
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // <------------------- POST METHOD -------------------->
    @Operation(summary = "Save User", description = "This Endpoint Saves an New User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Saved"),
            @ApiResponse(responseCode = "400", description = "Empty Data"),
            @ApiResponse(responseCode = "401", description = "User Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody User user) {
        return userService.save(user);
    }

    // <-------------------- PUT METHOD -------------------->
    @Operation(summary = "Update User", description = "This Endpoint Updates an User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Updated"),
            @ApiResponse(responseCode = "400", description = "Empty Data"),
            @ApiResponse(responseCode = "401", description = "User Already Exists"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User user,
            @RequestHeader("Authorization") String authorization) {
        return userService.update(id, user, authorization);
    }

    // <-------------------- DELETE METHOD -------------------->
    @Operation(summary = "Delete User", description = "Only Admin Users Can Use This Endpoint", security = {
            @SecurityRequirement(name = "Bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Deleted"),
            @ApiResponse(responseCode = "500", description = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
