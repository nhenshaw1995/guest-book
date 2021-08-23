package com.bt.tech.test.GuestBook.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bt.tech.test.GuestBook.pojo.SavedUser;
import com.bt.tech.test.GuestBook.pojo.User;
import com.bt.tech.test.GuestBook.roles.Roles;
import com.bt.tech.test.GuestBook.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController classUnderTest;

    @Test
    void testGetUserByUsernameAndPassword() {
        final var user = getUser();
        final var expectedSavedUser = getSavedUser();

        when(userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(expectedSavedUser);

        assertThat(classUnderTest.getUserByUsernameAndPassword(user)).isEqualTo(expectedSavedUser);
    }

    @Test
    void testSaveNewUser() {
        final var user = getUser();
        final var expectedSavedUser = getSavedUser();

        when(userService.saveNewUser(user)).thenReturn(expectedSavedUser);

        assertThat(classUnderTest.saveNewUser(user))
                .isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(expectedSavedUser));
    }

    private User getUser() {
        final var user = new User();
        user.setUsername("username");
        user.setPassword("password");

        return user;
    }

    private SavedUser getSavedUser() {
        final var savedUser = new SavedUser();
        savedUser.setUsername("username");
        savedUser.setRole(Roles.USER);

        return savedUser;
    }
}
