package org.sixtead.techrequests.service;

import org.sixtead.techrequests.domain.Role;

public interface RoleService {

    Role getById(Long id);
    Iterable<Role> getAll();
    Role create(Role role);
}
