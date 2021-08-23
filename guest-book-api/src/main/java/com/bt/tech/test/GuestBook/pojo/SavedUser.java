package com.bt.tech.test.GuestBook.pojo;

import com.bt.tech.test.GuestBook.roles.Roles;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class SavedUser {

    private String username;

    private Roles role;
}
