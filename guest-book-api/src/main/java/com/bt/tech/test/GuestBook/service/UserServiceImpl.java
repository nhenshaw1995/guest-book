package com.bt.tech.test.GuestBook.service;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.exception.UserNotFoundException;
import com.bt.tech.test.GuestBook.pojo.SavedUser;
import com.bt.tech.test.GuestBook.pojo.User;
import com.bt.tech.test.GuestBook.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Function<User, UserEntity> userToUserEntity;
    private final Function<UserEntity, SavedUser> userEntityToSavedUser;
    private final UnaryOperator<String> passwordEncoder;

    @Override
    public SavedUser getUserByUsernameAndPassword(String username, String password) {
        // @formatter:off
        return userRepository
                .findByUsernameAndPassword(username, passwordEncoder.apply(password))
                .map(userEntityToSavedUser::apply)
                .orElseThrow(UserNotFoundException::new);
        // @formatter:on
    }

    @Override
    public SavedUser saveNewUser(User user) {
        final var userEntity = userToUserEntity.apply(user);
        userEntity.setPassword(passwordEncoder.apply(user.getPassword()));

        final var savedUserEntity = userRepository.save(userEntity);

        return userEntityToSavedUser.apply(savedUserEntity);
    }

}
