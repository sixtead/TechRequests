package org.sixtead.techrequests.service;

import org.sixtead.techrequests.domain.User;

public interface UserService {
    User getById(Long id);
    Iterable<User> getAll();
    User create(User user);
    User update(User user);
    void delete(User user);
}
