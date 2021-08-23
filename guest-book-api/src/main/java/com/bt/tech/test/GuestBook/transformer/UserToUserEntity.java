package com.bt.tech.test.GuestBook.transformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.pojo.User;
import com.bt.tech.test.GuestBook.roles.Roles;

@Component
public class UserToUserEntity implements Function<User, UserEntity> {

    @Override
    public UserEntity apply(User user) {
        final var userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(Roles.USER);

        return userEntity;
    }

}
