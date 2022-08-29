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

import com.stefanini.spotifanini.model.User;
import com.stefanini.spotifanini.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // <-------------------- GET All Users -------------------->
    @ApiOperation(value = "Get All Users", notes = "This Endpoint Provides The List of All Users With Pagination")
    @GetMapping
    public Page<User> findAllUsers(
            @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 20) Pageable pagination) {

        return userService.findAllUsers(pagination);
    }

    // <-------------------- GET User -------------------->
    @ApiOperation(value = "Get User", notes = "This Endpoint Provides The User by The ID")
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // <------------------- POST METHOD -------------------->
    @ApiOperation(value = "Save User", notes = "This Endpoint Saves an New User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Saved"),
            @ApiResponse(code = 400, message = "Empty Data"),
            @ApiResponse(code = 401, message = "User Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PostMapping
    public ResponseEntity<String> save(@RequestBody User user) {
        return userService.save(user);
    }

    // <-------------------- PUT METHOD -------------------->
    @ApiOperation(value = "Update User", notes = "This Endpoint Updates an User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Updated"),
            @ApiResponse(code = 400, message = "Empty Data"),
            @ApiResponse(code = 401, message = "User Already Exists"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    // <-------------------- DELETE METHOD -------------------->
    @ApiOperation(value = "Delete User", notes = "This Endpoint Deletes an User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Deleted"),
            @ApiResponse(code = 500, message = "Server Side Exception")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
