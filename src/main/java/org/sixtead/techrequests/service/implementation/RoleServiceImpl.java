package org.sixtead.techrequests.service.implementation;

import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.repository.RoleRepository;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;


    @Override
    public Role save(@Valid Role role) {
        return repository.save(role);
    }

    @Override
    public Role getById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Role getByName(String name) {
        return repository.findFirstByName(name).orElse(null);
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Role role) {
        repository.delete(role);
    }

    @Override
    public boolean isNameUnique(Role role) {
        Optional<Role> found = repository.findFirstByName(role.getName());
        return !found.isPresent() || Objects.equals(role.getId(), found.get().getId());
    }

}
