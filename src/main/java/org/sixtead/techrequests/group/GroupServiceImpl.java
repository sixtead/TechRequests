package org.sixtead.techrequests.group;

import org.sixtead.techrequests.exceptions.NotFoundException;
import org.sixtead.techrequests.exceptions.NotNullConstraintException;
import org.sixtead.techrequests.exceptions.ServiceException;
import org.sixtead.techrequests.exceptions.UniqueConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group getById(Long id) throws NotFoundException {
        return groupRepository.findById(id).orElseThrow(() -> new NotFoundException("no such id"));
    }

    @Override
    public Iterable<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group create(Group group) throws ServiceException {
        return persist(group);
    }

    @Override
    public Group update(Group group) throws ServiceException {
        return persist(group);
    }

    private Group persist(Group group) {
        if (group.getName() == null || group.getName().isEmpty()) {
            throw new NotNullConstraintException("name is null");
        }
        if (groupRepository.findFirstByName(group.getName()).isPresent()) {
            throw new UniqueConstraintException("name is not unique");
        }
        return groupRepository.save(group);
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }
}
