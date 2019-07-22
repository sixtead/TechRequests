package org.sixtead.techrequests.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group getById(Long id) throws NullPointerException {
        return groupRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public Iterable<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }
}
