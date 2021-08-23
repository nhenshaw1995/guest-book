package com.bt.tech.test.GuestBook.transformer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.pojo.User;
import com.bt.tech.test.GuestBook.roles.Roles;

class UserToUserEntityTest {

    @Test
    void testApply() {
        final var classUnderTest = new UserToUserEntity();

        final var user = new User();
        user.setUsername("username");
        user.setPassword("password");

        final var userEntity = new UserEntity();
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setRole(Roles.USER);

        assertThat(classUnderTest.apply(user)).isEqualTo(userEntity);
    }

}
