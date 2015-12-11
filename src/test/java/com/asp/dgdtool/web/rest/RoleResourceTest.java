package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Role;
import com.asp.dgdtool.repository.RoleRepository;
import com.asp.dgdtool.repository.search.RoleSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RoleResource REST controller.
 *
 * @see RoleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RoleResourceTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_STATUS_ID = 1;
    private static final Integer UPDATED_STATUS_ID = 2;
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_MODIFIED_BY = "AAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private RoleSearchRepository roleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRoleMockMvc;

    private Role role;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoleResource roleResource = new RoleResource();
        ReflectionTestUtils.setField(roleResource, "roleRepository", roleRepository);
        ReflectionTestUtils.setField(roleResource, "roleSearchRepository", roleSearchRepository);
        this.restRoleMockMvc = MockMvcBuilders.standaloneSetup(roleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        role = new Role();
        role.setRole_name(DEFAULT_ROLE_NAME);
        role.setDescription(DEFAULT_DESCRIPTION);
        role.setStatus_id(DEFAULT_STATUS_ID);
        role.setCreated_by(DEFAULT_CREATED_BY);
        role.setCreated_date(DEFAULT_CREATED_DATE);
        role.setModified_by(DEFAULT_MODIFIED_BY);
        role.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createRole() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isCreated());

        // Validate the Role in the database
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeCreate + 1);
        Role testRole = roles.get(roles.size() - 1);
        assertThat(testRole.getRole_name()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRole.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testRole.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRole.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRole.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRole.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkRole_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setRole_name(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setDescription(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setStatus_id(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setCreated_by(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setCreated_date(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setModified_by(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setModified_date(null);

        // Create the Role, which fails.

        restRoleMockMvc.perform(post("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());

        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roles
        restRoleMockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
                .andExpect(jsonPath("$.[*].role_name").value(hasItem(DEFAULT_ROLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.role_name").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

		int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role
        role.setRole_name(UPDATED_ROLE_NAME);
        role.setDescription(UPDATED_DESCRIPTION);
        role.setStatus_id(UPDATED_STATUS_ID);
        role.setCreated_by(UPDATED_CREATED_BY);
        role.setCreated_date(UPDATED_CREATED_DATE);
        role.setModified_by(UPDATED_MODIFIED_BY);
        role.setModified_date(UPDATED_MODIFIED_DATE);

        restRoleMockMvc.perform(put("/api/roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roles.get(roles.size() - 1);
        assertThat(testRole.getRole_name()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRole.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testRole.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRole.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRole.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRole.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

		int databaseSizeBeforeDelete = roleRepository.findAll().size();

        // Get the role
        restRoleMockMvc.perform(delete("/api/roles/{id}", role.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
