package org.sixtead.techrequests.service.implementation;

import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.exceptions.NotFoundException;
import org.sixtead.techrequests.repository.RoleRepository;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role create(Role role) {
        role.setName(role.getName().toUpperCase());

        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }

        return roleRepository.save(role);
    }
}
