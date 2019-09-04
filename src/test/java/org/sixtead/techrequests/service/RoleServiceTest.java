package org.sixtead.techrequests.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.repository.RoleRepository;
import org.sixtead.techrequests.service.implementation.RoleServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityNotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleService service = new RoleServiceImpl();

    private Role role;
    private Role savedRole;
    private List<Role> roles;


    @Before
    public void setUp() {
        role = new Role("role");

        savedRole = new Role();
        savedRole.setId(1L);
        savedRole.setName("ROLE_ROLE");
        savedRole.setCreatedAt(Timestamp.from(Instant.now()));
        savedRole.setUpdatedAt(Timestamp.from(Instant.now()));

        roles = new ArrayList<>();
    }

    @Test
    public void save_withNullName_shouldThrowTransactionSystemException() {
        when(repository.save(role)).thenThrow(new TransactionSystemException("Could not commit JPA transaction"));

        assertThatThrownBy(() -> service.save(role))
                .isInstanceOf(TransactionSystemException.class);
    }

    @Test
    public void save_withUniqueName_shouldReturnRoleWithPrefixedAndUppercaseName() {
        when(repository.save(role)).thenReturn(role);

        assertThat(role)
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("name", role.getName());

        role.setId(savedRole.getId());
        role.setCreatedAt(savedRole.getCreatedAt());
        role.setUpdatedAt(savedRole.getUpdatedAt());

        assertThat(service.save(role))
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("name", savedRole.getName());
    }

    @Test
    public void save_withNonUniqueName_shouldThrowDataIntegrityViolationException() {
        when(repository.save(role)).thenThrow(new DataIntegrityViolationException(""));

        assertThatThrownBy(() -> service.save(role))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void getById_withNullId_shouldThrowIllegalArgumentException() {
        when(repository.findById(null)).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> service.getById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getById_withExistentId_shouldReturnRole() {
        when(repository.findById(savedRole.getId())).thenReturn(Optional.of(savedRole));

        assertThat(service.getById(savedRole.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(savedRole);
    }

    @Test
    public void getById_withNonExistentId_shouldThrowEntityNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void getByName_withNullName_shouldReturnNull() {
        when(repository.findFirstByName(null)).thenReturn(Optional.empty());

        assertThat(service.getByName(null))
                .isNull();
    }

    @Test
    public void getByName_withExistentName_shouldReturnRole() {
        when(repository.findFirstByName(savedRole.getName())).thenReturn(Optional.of(savedRole));

        assertThat(service.getByName(savedRole.getName()))
                .isNotNull()
                .isEqualToComparingFieldByField(savedRole);
    }

    @Test
    public void getByName_withNonExistentName_shouldReturnNull() {
        when(repository.findFirstByName("non-existent")).thenReturn(Optional.empty());

        assertThat(service.getByName("non-existent"))
                .isNull();
    }

    @Test
    public void getAll_withZeroRoles_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(roles);

        assertThat(service.getAll())
                .isInstanceOf(List.class)
                .hasSize(0);
    }

    @Test
    public void getAll_withNonZeroRoles_shouldReturnNonEmptyList() {
        roles.add(savedRole);

        when(repository.findAll()).thenReturn(roles);

        assertThat(service.getAll())
                .isInstanceOf(List.class)
                .hasSize(1);
    }

    @Test
    public void delete_withNullRole_shouldThrowIllegalArgumentException() {
        doThrow(new IllegalArgumentException()).when(repository).delete(null);

        assertThatThrownBy(() -> service.delete(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void delete_withExistentRole_shouldDeleteRole() {
        when(repository.findAll()).thenReturn(roles);
        doNothing().when(repository).delete(savedRole);

        roles.add(savedRole);

        assertThat(service.getAll())
                .hasSize(1);

        service.delete(role);

        roles.remove(savedRole);

        assertThat(service.getAll())
                .hasSize(0);
    }

    @Test
    public void delete_withNonExistentRole_shouldNotDeleteAnything() {
        when(repository.findAll()).thenReturn(roles);
        doNothing().when(repository).delete(role);

        roles.add(savedRole);

        assertThat(service.getAll())
                .hasSize(1);

        service.delete(role);

        assertThat(service.getAll())
                .hasSize(1);
    }

    @Test
    public void isNameUnique_withNewRoleAndUniqueName_shouldReturnTrue() {
        when(repository.findFirstByName(role.getName())).thenReturn(Optional.empty());

        assertThat(service.isNameUnique(role))
                .isTrue();
    }

    @Test
    public void isNameUnique_withNewRoleAndNonUniqueName_shouldReturnFalse() {
        when(repository.findFirstByName(role.getName())).thenReturn(Optional.of(savedRole));

        assertThat(service.isNameUnique(role))
                .isFalse();
    }

    @Test
    public void isNameUnique_withExistingRoleAndSameName_shouldReturnTrue() {
        when(repository.findFirstByName(savedRole.getName())).thenReturn(Optional.of(savedRole));

        assertThat(service.isNameUnique(savedRole))
                .isTrue();
    }
}