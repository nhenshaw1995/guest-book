package com.bt.tech.test.GuestBook.service;

import com.bt.tech.test.GuestBook.pojo.SavedUser;
import com.bt.tech.test.GuestBook.pojo.User;

public interface UserService {
    public SavedUser getUserByUsernameAndPassword(String username, String password);

    public SavedUser saveNewUser(User user);
}
