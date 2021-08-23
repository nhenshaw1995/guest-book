package com.bt.tech.test.GuestBook;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.repository.UserRepository;
import com.bt.tech.test.GuestBook.roles.Roles;
import com.bt.tech.test.GuestBook.transformer.PasswordEncoder;

class DataLoaderTest {

    @Test
    void testRun() {
        final var mockRepo = mock(UserRepository.class);
        final var mockEncoder = mock(PasswordEncoder.class);
        final var classUnderTest = new DataLoader(mockRepo, mockEncoder);

        final var password = "p@ssword";
        final var encodedPassword = "encodedP@ssword";
        when(mockEncoder.apply(password)).thenReturn(encodedPassword);

        classUnderTest.run(null);

        final var expectedUser = new UserEntity();
        expectedUser.setUsername("admin");
        expectedUser.setPassword(encodedPassword);
        expectedUser.setRole(Roles.ADMIN);

        verify(mockRepo).save(expectedUser);
    }

}
