package com.bt.tech.test.GuestBook.transformer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.pojo.SavedUser;
import com.bt.tech.test.GuestBook.roles.Roles;

class UserEntityToSavedUserTest {

    @Test
    void testApply() {
        final var classUnderTest = new UserEntityToSavedUser();

        final var userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setRole(Roles.ADMIN);

        final var savedUser = new SavedUser();

        savedUser.setUsername("username");
        savedUser.setRole(Roles.ADMIN);

        assertThat(classUnderTest.apply(userEntity)).isEqualTo(savedUser);
    }

}
