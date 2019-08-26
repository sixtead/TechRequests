package org.sixtead.techrequests.group;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sixtead.techrequests.domain.Group;
import org.sixtead.techrequests.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void group_repository_should_be_empty() {
        assertThat(groupRepository.count()).isEqualTo(0);
    }

    @Test
    public void should_return_null_if_group_not_found_by_name() {
        assertThat(groupRepository.findFirstByName("groupname").orElse(null)).isNull();
    }

    @Test
    public void should_return_group_with_same_name_as_saved() {
        Group group = new Group("groupname");
        entityManager.persistAndFlush(group);

        Group shouldBeFound = groupRepository.findFirstByName("groupname").orElse(null);

        assertThat(shouldBeFound).isNotNull();
        assertThat(shouldBeFound.getName()).isEqualTo(group.getName());
    }
}