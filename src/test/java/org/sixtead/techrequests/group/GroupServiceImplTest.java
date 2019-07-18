package org.sixtead.techrequests.group;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
public class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService = new GroupServiceImpl();

    @Test
    public void should_return_group_if_exists() {
        Group group = new Group("group");

        Mockito.when(groupRepository.findById(1L))
                .thenReturn(Optional.of(group));

        assertThat(groupService.getById(1L)).isEqualTo(group);
    }

    @Test
    public void should_throw_exception_if_not_exists() {
        Mockito.when(groupRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupService.getById(2L)).isInstanceOf(Exception.class);
    }

    @Test
    public void should_return_iterable() {
        Iterable<Group> groups = new ArrayList<>();

        Mockito.when(groupRepository.findAll())
                .thenReturn(groups);

        assertThat(groupService.getAll()).isInstanceOf(Iterable.class);
    }

    @Test
    public void should_contain_group() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group("group");
        groups.add(group);

        Mockito.when(groupRepository.findAll())
                .thenReturn(groups);

        assertThat(groupService.getAll()).contains(group);
    }

    @Test
    public void should_throw_exception_when_name_is_null() {
        Group group = new Group();

        assertThatThrownBy(() -> groupService.create(group)).isInstanceOf(Exception.class);
    }

    @Test
    public void should_throw_exception_when_name_is_blank() {
        Group group = new Group("");

        assertThatThrownBy(() -> groupService.create(group)).isInstanceOf(Exception.class);
    }

    @Test
    public void should_throw_exception_when_name_is_not_unique() {
        Group group = new Group("group");

        Mockito.when(groupRepository.findFirstByName(group.getName()))
                .thenReturn(Optional.of(group));

        assertThatThrownBy(() -> groupService.create(group)).isInstanceOf(Exception.class);
    }

//    @Test
//    public void should_return_group_when_created() {
//        Group group = new Group("group");
//
//        Mockito.when(groupRepository.findFirstByName(group.getName()))
//                .thenReturn(Optional.empty());
//
//        Mockito.when(groupRepository.save(group))
//                .thenCallRealMethod();
//    }
}