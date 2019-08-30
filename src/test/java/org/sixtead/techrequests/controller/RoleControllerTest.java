package org.sixtead.techrequests.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest(RoleController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleService service;

    @Test
    @DirtiesContext
    public void index_shouldReturnIndexViewWithRolesAttribute() throws Exception {
        List<Role> roles = service.getAll();

        mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/index"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attribute("roles", roles));
    }

    @Test
    @DirtiesContext
    public void index_shouldReturnViewContainingRoleNameWithEditLinkAndDeleteLink() throws Exception {
        Role role = service.save(new Role("role"));

        mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(role.getName())))
                .andExpect(content().string(containsString("<a href=\"/roles/" + role.getId() + "\">" + role.getName() + "</a>")))
                .andExpect(content().string(containsString("<a href=\"/roles/" + role.getId() + "/delete\">delete</a>")));
    }

    @Test
    @DirtiesContext
    public void add_shouldReturnRoleViewContainingNameInput() throws Exception {
        mvc.perform(get("/roles/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/add"))
                .andExpect(content().string(containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\"")));
    }

    @Test
    @DirtiesContext
    public void edit_withValidId_shouldReturnRoleViewContainingNameInput() throws Exception {
        Role role = service.save(new Role("role"));

        mvc.perform(get("/roles/" + role.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/edit"))
                .andExpect(content().string(containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\"")));
    }

    @Test
    @DirtiesContext
    public void edit_withInvalidId_shouldReturnView404() throws Exception {
        mvc.perform(get("roles/1/edit"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("404"));
    }

    @Test
    @DirtiesContext
    public void create_withValidName_shouldRedirectToIndexView() throws Exception {
        mvc.perform(post("/roles/create").with(csrf()).param("name", "valid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    @DirtiesContext
    public void create_withBlankName_shouldReturnRoleViewContainingNameNotBlankError() throws Exception {
        mvc.perform(post("/roles/create").with(csrf()).param("name", ""))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "NotBlank"));
    }

    @Test
    @DirtiesContext
    public void create_withNonUniqueName_shouldReturnRoleViewContainingNameUniqueError() throws Exception {
        Role role = service.save(new Role("role"));

        mvc.perform(post("/roles/create").with(csrf()).param("name", role.getName()))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "Unique"));
    }

    @Test
    @DirtiesContext
    public void update_withSameName_shouldRedirectToIndexView() throws Exception {
        Role role = service.save(new Role("role"));

        mvc.perform(post("/roles/" + role.getId() + "/update").with(csrf()).param("name", role.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    @DirtiesContext
    public void update_withValidName_shouldRedirectToIndexView() throws Exception {
        Role role = service.save(new Role("role"));

        mvc.perform(post("/roles/" + role.getId() + "/update").with(csrf()).param("name", "super-role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    @DirtiesContext
    public void update_withBlankName_shouldReturnRoleViewContainingNameNotBlankError() throws Exception {
        Role role = service.save(new Role("role"));

        mvc.perform(post("/roles/" + role.getId() + "/update").with(csrf()).param("name", ""))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "NotBlank"));
    }

    @Test
    @DirtiesContext
    public void update_withNonUniqueName_shouldReturnRoleViewContainingNameUniqueError() throws Exception {
        Role role = service.save(new Role("role"));
        service.save(new Role("super-role"));

        mvc.perform(post("/roles/" + role.getId() + "/update").with(csrf()).param("name", "super-role"))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "Unique"));
    }

}