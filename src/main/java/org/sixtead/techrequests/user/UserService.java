package org.sixtead.techrequests.user;

public interface UserService {
    User getById(Long id);
    Iterable<User> getAll();
    User create(User user);
    User update(User user);
    void delete(User user);
}
