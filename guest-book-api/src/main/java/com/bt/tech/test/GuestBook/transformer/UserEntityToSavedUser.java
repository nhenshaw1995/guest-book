package com.bt.tech.test.GuestBook.transformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.pojo.SavedUser;

@Component
public class UserEntityToSavedUser implements Function<UserEntity, SavedUser> {

    @Override
    public SavedUser apply(UserEntity userEntity) {
        final var user = new SavedUser();

        user.setUsername(userEntity.getUsername());
        user.setRole(userEntity.getRole());

        return user;
    }

}
