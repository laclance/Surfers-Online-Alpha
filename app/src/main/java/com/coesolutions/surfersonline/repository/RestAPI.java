package com.coesolutions.surfersonline.repository;

import java.util.List;

public interface RestAPI<S, ID> {
    S get(ID id);

    String post(S entity);

    String put(S entity);

    void delete(S entity);

    List<S> findAll();
}
