package com.coesolutions.surfersonline.repository;

import com.coesolutions.surfersonline.model.User;

public interface RestUserAPI extends RestAPI<User,Long> {
    User get(String username);
}
