package org.sixtead.techrequests.group;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sixtead.techrequests.exceptions.NotFoundException;
import org.sixtead.techrequests.exceptions.UniqueConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.PushBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupService groupService;

    @Test
    public void should_contain_table_with_all_groups() throws Exception {
        List<Group> groups = new ArrayList<>();
        Group users = new Group("users");
        Long id = 1L;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        users.setId(id);
        users.setCreatedAt(timestamp);
        users.setUpdatedAt(timestamp);

        groups.add(users);

        Mockito.when(groupService.getAll()).thenReturn(groups);

        mvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("groups", groups))
                .andExpect(content().string(Matchers.containsString("<table")))
                .andExpect(content().string(Matchers.containsString(users.getId().toString())))
                .andExpect(content().string(Matchers.containsString(users.getName())))
                .andExpect(content().string(Matchers.containsString(users.getCreatedAt().toString())))
                .andExpect(content().string(Matchers.containsString(users.getUpdatedAt().toString())));
    }

    @Test
    public void should_show_form_to_add_group() throws Exception {
        mvc.perform(get("/groups/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(Matchers.containsString("<form action=\"/groups/add\" method=\"post\"")));
    }

    @Test
    public void should_redirect_to_all_groups_when_added_group() throws Exception {
        Group users = new Group("users");

        Mockito.when(groupService.create(users)).thenReturn(users);

        mvc.perform(post("/groups/add").param("name", "users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }

    @Test
    public void should_show_form_validation_error_when_name_is_empty() throws Exception {
        mvc.perform(post("/groups/add").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("<div class=\"invalid-feedback d-block\"")));
    }

    @Test
    public void should_show_form_validation_error_when_name_is_not_unique() throws Exception {
        Mockito.when(groupService.create(new Group("users"))).thenThrow(new UniqueConstraintException());

        mvc.perform(post("/groups/add").param("name", "users"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("<div class=\"invalid-feedback d-block\"")));
    }

    @Test
    public void should_show_edit_form_for_group_if_found() throws Exception {
        Group group = new Group("users");
        group.setId(1L);

        Mockito.when(groupService.getById(group.getId())).thenReturn(group);

        mvc.perform(get("/groups/edit/{id}", group.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("group", group))
                .andExpect(content().string(Matchers.containsString("<input class=\"btn btn-primary\" type=\"submit\" value=\"Save Group\">")));
    }

    @Test
    public void should_redirect_to_404_if_group_not_found() throws Exception {
        Mockito.when(groupService.getById(1L)).thenThrow(new NotFoundException());

        mvc.perform(get("/groups/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));
    }
}