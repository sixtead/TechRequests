package org.sixtead.techrequests.roles;

public interface RoleService {

    Role getById(Long id);
    Iterable<Role> getAll();
    Role create(Role role);
}
