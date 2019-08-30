package org.sixtead.techrequests.service;

import org.sixtead.techrequests.domain.Role;

import javax.validation.Valid;
import java.util.List;

public interface RoleService {

    Role save(@Valid Role role);
    Role getById(Long id);
    Role getByName(String name);
    List<Role> getAll();
    void delete(Role role);

    boolean isNameUnique(Role role);
}
