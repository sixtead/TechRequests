package org.sixtead.techrequests.service;

import org.sixtead.techrequests.domain.Group;

public interface GroupService {
    Group getById(Long id);
    Iterable<Group> getAll();
    Group create(Group group);
    Group update(Group group);
    void delete(Group group);
}