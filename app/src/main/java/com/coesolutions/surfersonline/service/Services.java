package com.coesolutions.surfersonline.service;

import java.util.List;

public interface Services<S, ID> {

    S findById(ID id);

    String save(S entity);

    String update(S entity);

    void delete(S entity);

    List<S> findAll();
}
