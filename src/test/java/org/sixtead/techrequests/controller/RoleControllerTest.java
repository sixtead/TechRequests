package org.sixtead.techrequests.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleService service;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private Role role;
    private Role savedRole;
    private List<Role> roles;

    @Before
    public void setUp() {
        role = new Role("role");

        savedRole = new Role("ROLE_ROLE");
        savedRole.setId(1L);
        savedRole.setCreatedAt(Timestamp.from(Instant.now()));
        savedRole.setUpdatedAt(Timestamp.from(Instant.now()));

        roles = new ArrayList<>();
    }

    @Test
    public void index_shouldReturnIndexViewWithRolesAttribute() throws Exception {
        when(service.getAll()).thenReturn(roles);

        mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/index"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attribute("roles", roles));
    }

    @Test
    public void index_shouldReturnViewContainingRoleNameWithEditLinkAndDeleteLink() throws Exception {
        when(service.getAll()).thenReturn(roles);

        roles.add(savedRole);

        mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(savedRole.getName())))
                .andExpect(content().string(containsString("<a href=\"/roles/" + savedRole.getId() + "/edit\">" + savedRole.getName() + "</a>")))
                .andExpect(content().string(containsString("<a href=\"/roles/" + savedRole.getId() + "/delete\">delete</a>")));
    }

    @Test
    public void add_shouldReturnRoleViewContainingNameInput() throws Exception {
        mvc.perform(get("/roles/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/add"))
                .andExpect(content().string(containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\"")));
    }

    @Test
    public void edit_withValidId_shouldReturnRoleViewContainingNameInput() throws Exception {
        when(service.getById(savedRole.getId())).thenReturn(savedRole);

        mvc.perform(get("/roles/" + savedRole.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/edit"))
                .andExpect(content().string(containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\"")));
    }

    @Test
    public void edit_withInvalidId_shouldReturnView404() throws Exception {
        when(service.getById(1L)).thenThrow(new EntityNotFoundException());

        mvc.perform(get("roles/1/edit"))
                .andExpect(status().is4xxClientError());
//                .andExpect(view().name("404"));
    }

    @Test
    public void create_withValidName_shouldRedirectToIndexView() throws Exception {
        when(service.isNameUnique(role)).thenReturn(true);

        mvc.perform(post("/roles/create").with(csrf()).param("name", role.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    public void create_withBlankName_shouldReturnRoleViewContainingNameNotBlankError() throws Exception {
        mvc.perform(post("/roles/create").with(csrf()).param("name", ""))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "NotBlank"));
    }

    @Test
    public void create_withNonUniqueName_shouldReturnRoleViewContainingNameUniqueError() throws Exception {
        when(service.isNameUnique(role)).thenReturn(false);

        mvc.perform(post("/roles/create").with(csrf()).param("name", role.getName()))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "Unique"));
    }

    @Test
    public void update_withSameName_shouldRedirectToIndexView() throws Exception {
        when(service.isNameUnique(savedRole)).thenReturn(true);

        mvc.perform(post("/roles/" + savedRole.getId() + "/update").with(csrf()).param("id", savedRole.getId().toString()).param("name", savedRole.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    public void update_withValidName_shouldRedirectToIndexView() throws Exception {
        when(service.isNameUnique(savedRole)).thenReturn(true);

        mvc.perform(post("/roles/" + savedRole.getId() + "/update").with(csrf()).param("id", savedRole.getId().toString()).param("name", "super-role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    public void update_withBlankName_shouldReturnRoleViewContainingNameNotBlankError() throws Exception {

        mvc.perform(post("/roles/" + savedRole.getId() + "/update").with(csrf()).param("name", ""))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "NotBlank"));
    }

    @Test
    public void update_withNonUniqueName_shouldReturnRoleViewContainingNameUniqueError() throws Exception {
        when(service.isNameUnique(savedRole)).thenReturn(false);

        mvc.perform(post("/roles/" + savedRole.getId() + "/update").with(csrf()).param("name", "super-role"))
                .andExpect(model().attributeHasFieldErrorCode("role", "name", "Unique"));
    }

    @Test
    public void delete_withExistentId_shouldReturnIndex() throws Exception {
        doNothing().when(service).delete(savedRole);

        mvc.perform(get("/roles/" + savedRole.getId() + "/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/index"));
    }

    @Test
    public void delete_withNonExistentId_shouldReturnView404() throws Exception {
        when(service.getById(1L)).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/roles/1/delete"))
                .andExpect(status().is4xxClientError());
    }

}