package org.sixtead.TechRequests.group;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sixtead.techrequests.group.Group;
import org.sixtead.techrequests.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testFindFirstByName() {
        Iterable<Group> groups = groupRepository.findAll();

        assertNotEquals(0, StreamSupport.stream(groups.spliterator(), false).count());

        Group group = groupRepository.save(new Group("superusers"));
        Long id = group.getId();

        assertEquals(1L, Optional.ofNullable(id));
//        assertEquals(1L, (Long) group.getId());
////        entityManager.persistAndFlush(group);
//        groupRepository.save(group);
//
//        Optional<Group> foundOptionalGroup = groupRepository.findFirstByName("users");
//        Group foundGroup = foundOptionalGroup.orElse(null);
//
//        assertNotNull(foundGroup);
    }
}