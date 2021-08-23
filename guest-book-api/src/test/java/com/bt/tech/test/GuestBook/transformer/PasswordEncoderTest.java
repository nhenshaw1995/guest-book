package com.bt.tech.test.GuestBook.transformer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PasswordEncoderTest {

    @Test
    void testApply() {
        final var classUnderTest = new PasswordEncoder();
        assertThat(classUnderTest.apply("test password")).isEqualTo("dGVzdCBwYXNzd29yZA==");
    }

}
