package com.kaab.ecommerce.controller;

import com.kaab.ecommerce.entity.UserInfo;
import com.kaab.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "APIs for managing users")

public class UserController {

    private final UserService userService;

    // Inject UserService via constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody UserInfo userInfo) {
        String message = userService.addUser(userInfo);

        String userLocation = String.format("/api/users/%s", userInfo.getId());

        return ResponseEntity
                .created(URI.create(userLocation))  // Set the location of the newly created user
                .body(message);  // Return success message
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> getAllUsers() {

        return ResponseEntity
                .ok()
                .body(userService.getAllUser());
    }
}
