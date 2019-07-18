package org.sixtead.techrequests.group;

public interface GroupService {
    public Group getById(Long id);
    public Iterable<Group> getAll();
    public Group create(Group group);
}