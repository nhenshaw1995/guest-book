package com.bt.tech.test.GuestBook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bt.tech.test.GuestBook.pojo.SavedUser;
import com.bt.tech.test.GuestBook.pojo.User;
import com.bt.tech.test.GuestBook.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public SavedUser getUserByUsernameAndPassword(@RequestBody User user) {
        return userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @PostMapping("/new")
    public ResponseEntity<SavedUser> saveNewUser(@RequestBody User user) {
        final var savedUser = userService.saveNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
