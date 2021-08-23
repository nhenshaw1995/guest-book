package com.bt.tech.test.GuestBook.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.exception.UserNotFoundException;
import com.bt.tech.test.GuestBook.pojo.SavedUser;
import com.bt.tech.test.GuestBook.pojo.User;
import com.bt.tech.test.GuestBook.repository.UserRepository;
import com.bt.tech.test.GuestBook.roles.Roles;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Function<User, UserEntity> userToUserEntity;

    @Mock
    private Function<UserEntity, SavedUser> userEntityToSavedUser;

    @Mock
    private UnaryOperator<String> passwordEncoder;

    private UserServiceImpl classUnderTest;

    @BeforeEach
    void init() {
        classUnderTest = new UserServiceImpl(userRepository, userToUserEntity, userEntityToSavedUser, passwordEncoder);
    }

    @Test
    void testGetUserByUsernameAndPassword() {
        final var username = "username";
        final var password = "password";
        final var encodedPassword = "encodedPassword";

        final var expectedUserEntity = getUserEntity();
        final var expectedSavedUser = getSavedUser();
        when(passwordEncoder.apply(password)).thenReturn(encodedPassword);
        when(userRepository.findByUsernameAndPassword(username, encodedPassword))
                .thenReturn(Optional.of(expectedUserEntity));
        when(userEntityToSavedUser.apply(expectedUserEntity)).thenReturn(expectedSavedUser);

        assertThat(classUnderTest.getUserByUsernameAndPassword(username, password)).isEqualTo(expectedSavedUser);
    }

    @Test
    void testGetUserByUsernameAndPasswordUserNotFoundException() {
        final var username = "username";
        final var password = "password";
        final var encodedPassword = "encodedPassword";

        when(passwordEncoder.apply(password)).thenReturn(encodedPassword);
        when(userRepository.findByUsernameAndPassword(username, encodedPassword)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> classUnderTest.getUserByUsernameAndPassword(username, password))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testSaveNewUser() {
        final var user = new User();
        user.setUsername("username");
        user.setPassword("password");

        final var userEntity = getUserEntity();
        userEntity.setId(null);
        final var savedUserEntity = getUserEntity();
        final var savedUser = getSavedUser();

        when(userToUserEntity.apply(user)).thenReturn(userEntity);
        userEntity.setPassword("encoded password");
        when(passwordEncoder.apply(user.getPassword())).thenReturn(userEntity.getPassword());
        when(userRepository.save(userEntity)).thenReturn(savedUserEntity);
        when(userEntityToSavedUser.apply(savedUserEntity)).thenReturn(savedUser);

        assertThat(classUnderTest.saveNewUser(user)).isEqualTo(savedUser);
    }

    private UserEntity getUserEntity() {
        final var userEntity = new UserEntity();

        userEntity.setId(UUID.randomUUID());
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setRole(Roles.USER);

        return userEntity;
    }

    private SavedUser getSavedUser() {
        final var savedUser = new SavedUser();

        savedUser.setUsername("username");
        savedUser.setRole(Roles.USER);

        return savedUser;
    }
}
