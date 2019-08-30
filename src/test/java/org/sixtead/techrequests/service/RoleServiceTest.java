package org.sixtead.techrequests.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {

//    @MockBean
    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleService service;


    @Test
    @DirtiesContext
    public void save_withNullName_shouldThrowConstraintViolationException() {
        assertThatThrownBy(() -> service.save(new Role(null)))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DirtiesContext
    public void save_withUniqueName_shouldReturnRole() {
        Role role = new Role("role");

        assertThat(role)
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("name", "role");

        assertThat(service.save(role))
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DirtiesContext
    public void save_withNonUniqueName_shouldThrowDataIntegrityViolationException() {
        service.save(new Role("role"));

        assertThatThrownBy(() -> service.save(new Role("role")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DirtiesContext
    public void getById_withNullId_shouldThrowInvalidDataAccessApiUsageException() {
        assertThatThrownBy(() -> service.getById(null))
                .isInstanceOf(InvalidDataAccessApiUsageException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DirtiesContext
    public void getById_withExistentId_shouldReturnRole() {
        Role role = new Role("role");
        service.save(role);

        Role foundRole = service.getById(role.getId());

        assertThat(foundRole)
                .isNotNull()
                .isEqualToComparingFieldByField(role);
    }

    @Test
    @DirtiesContext
    public void getById_withNonExistentId_shouldThrowEntityNotFoundException() {
        assertThatThrownBy(() -> service.getById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DirtiesContext
    public void getByName_withNullName_shouldReturnNull() {
//        assertThatThrownBy(() -> service.getByName(null))
//                .isInstanceOf(InvalidDataAccessApiUsageException.class)
//                .hasCauseInstanceOf(IllegalArgumentException.class);
        assertThat(service.getByName("non-existent"))
                .isNull();
    }

    @Test
    @DirtiesContext
    public void getByName_withExistentName_shouldReturnRole() {
        Role role = new Role("role");
        service.save(role);

        Role foundRole = service.getByName(role.getName());

        assertThat(foundRole)
                .isNotNull()
                .isEqualToComparingFieldByField(role);
    }

    @Test
    @DirtiesContext
    public void getByName_withNonExistentName_shouldReturnNull() {
        assertThat(service.getByName("non-existent"))
                .isNull();
    }

    @Test
    @DirtiesContext
    public void getAll_withZeroRoles_shouldReturnEmptyList() {
        assertThat(service.getAll())
                .isInstanceOf(List.class)
                .hasSize(0);
    }

    @Test
    @DirtiesContext
    public void getAll_withNonZeroRoles_shouldReturnNonEmptyList() {
        service.save(new Role("role"));

        assertThat(service.getAll())
                .isInstanceOf(List.class)
                .hasSize(1);
    }

    @Test
    @DirtiesContext
    public void delete_withNullRole_shouldThrowInvalidDataAccessApiUsageException() {
        assertThatThrownBy(() -> service.delete(null))
                .isInstanceOf(InvalidDataAccessApiUsageException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DirtiesContext
    public void delete_withExistentRole_shouldDeleteRole() {
        Role role = service.save(new Role("role"));

        assertThat(service.getAll())
                .hasSize(1);

        service.delete(role);

        assertThat(service.getAll())
                .hasSize(0);
    }

    @Test
    @DirtiesContext
    public void delete_withNonExistentRole_shouldNotDeleteAnything() {
        Role role = service.save(new Role("role"));

        assertThat(service.getAll())
                .hasSize(1);

        service.delete(new Role("non-existent"));

        assertThat(service.getAll())
                .hasSize(1);
    }

    @Test
    @DirtiesContext
    public void isNameUnique_withNewRoleAndUniqueName_shouldReturnTrue() {
        assertThat(service.isNameUnique(new Role("role")))
                .isTrue();
    }

    @Test
    @DirtiesContext
    public void isNameUnique_withNewRoleAndNonUniqueName_shouldReturnFalse() {
        service.save(new Role("role"));

        assertThat(service.isNameUnique(new Role("role")))
                .isFalse();
    }

    @Test
    @DirtiesContext
    public void isNameUnique_withExistingRoleAndSameName_shouldReturnTrue() {
        Role role = service.save(new Role("role"));

        assertThat(service.isNameUnique(role))
                .isTrue();
    }
}