package com.coesolutions.surfersonline.service;

import com.coesolutions.surfersonline.model.User;

public interface UserService extends Services<User,Long>  {
    User findByUsername(String username);
}
