package com.coesolutions.surfersonline.service.impl;

import com.coesolutions.surfersonline.model.User;
import com.coesolutions.surfersonline.repository.impl.RestUserAPIImpl;
import com.coesolutions.surfersonline.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    final RestUserAPIImpl rest = new RestUserAPIImpl();
    @Override
    public User findById(Long id) {
        return rest.get(id);
    }

    @Override
    public User findByUsername(String username) {
        return rest.get(username);
    }

    @Override
    public String save(User entity) {
        return rest.post(entity);
    }

    @Override
    public String update(User entity) {
        return rest.put(entity);
    }

    @Override
    public void delete(User entity) {
        rest.delete(entity);
    }

    @Override
    public List<User> findAll() {
        return rest.findAll();
    }
}
