package org.sixtead.techrequests.group;

public interface GroupService {
    public Group getById(Long id);
    public Group getByName(String name);
    public Iterable<Group> getAll();
    public Group save(Group group);
}