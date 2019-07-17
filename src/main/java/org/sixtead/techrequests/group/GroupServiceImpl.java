package org.sixtead.techrequests.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group getById(Long id) {
        return groupRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public Group getByName(String name) {
        return groupRepository.findFirstByName(name).orElseThrow(NullPointerException::new);
    }

    @Override
    public Iterable<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
}
