package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Server_role;
import com.asp.dgdtool.repository.Server_roleRepository;
import com.asp.dgdtool.repository.search.Server_roleSearchRepository;

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
 * Test class for the Server_roleResource REST controller.
 *
 * @see Server_roleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Server_roleResourceTest {

    private static final String DEFAULT_SERVER_ROLE_NAME = "AAAAA";
    private static final String UPDATED_SERVER_ROLE_NAME = "BBBBB";
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
    private Server_roleRepository server_roleRepository;

    @Inject
    private Server_roleSearchRepository server_roleSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServer_roleMockMvc;

    private Server_role server_role;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Server_roleResource server_roleResource = new Server_roleResource();
        ReflectionTestUtils.setField(server_roleResource, "server_roleRepository", server_roleRepository);
        ReflectionTestUtils.setField(server_roleResource, "server_roleSearchRepository", server_roleSearchRepository);
        this.restServer_roleMockMvc = MockMvcBuilders.standaloneSetup(server_roleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        server_role = new Server_role();
        server_role.setServer_role_name(DEFAULT_SERVER_ROLE_NAME);
        server_role.setDescription(DEFAULT_DESCRIPTION);
        server_role.setStatus_id(DEFAULT_STATUS_ID);
        server_role.setCreated_by(DEFAULT_CREATED_BY);
        server_role.setCreated_date(DEFAULT_CREATED_DATE);
        server_role.setModified_by(DEFAULT_MODIFIED_BY);
        server_role.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createServer_role() throws Exception {
        int databaseSizeBeforeCreate = server_roleRepository.findAll().size();

        // Create the Server_role

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isCreated());

        // Validate the Server_role in the database
        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeCreate + 1);
        Server_role testServer_role = server_roles.get(server_roles.size() - 1);
        assertThat(testServer_role.getServer_role_name()).isEqualTo(DEFAULT_SERVER_ROLE_NAME);
        assertThat(testServer_role.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServer_role.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testServer_role.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServer_role.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServer_role.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServer_role.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkServer_role_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setServer_role_name(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setDescription(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setStatus_id(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setCreated_by(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setCreated_date(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setModified_by(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_roleRepository.findAll().size();
        // set the field null
        server_role.setModified_date(null);

        // Create the Server_role, which fails.

        restServer_roleMockMvc.perform(post("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isBadRequest());

        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServer_roles() throws Exception {
        // Initialize the database
        server_roleRepository.saveAndFlush(server_role);

        // Get all the server_roles
        restServer_roleMockMvc.perform(get("/api/server_roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(server_role.getId().intValue())))
                .andExpect(jsonPath("$.[*].server_role_name").value(hasItem(DEFAULT_SERVER_ROLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getServer_role() throws Exception {
        // Initialize the database
        server_roleRepository.saveAndFlush(server_role);

        // Get the server_role
        restServer_roleMockMvc.perform(get("/api/server_roles/{id}", server_role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(server_role.getId().intValue()))
            .andExpect(jsonPath("$.server_role_name").value(DEFAULT_SERVER_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServer_role() throws Exception {
        // Get the server_role
        restServer_roleMockMvc.perform(get("/api/server_roles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServer_role() throws Exception {
        // Initialize the database
        server_roleRepository.saveAndFlush(server_role);

		int databaseSizeBeforeUpdate = server_roleRepository.findAll().size();

        // Update the server_role
        server_role.setServer_role_name(UPDATED_SERVER_ROLE_NAME);
        server_role.setDescription(UPDATED_DESCRIPTION);
        server_role.setStatus_id(UPDATED_STATUS_ID);
        server_role.setCreated_by(UPDATED_CREATED_BY);
        server_role.setCreated_date(UPDATED_CREATED_DATE);
        server_role.setModified_by(UPDATED_MODIFIED_BY);
        server_role.setModified_date(UPDATED_MODIFIED_DATE);

        restServer_roleMockMvc.perform(put("/api/server_roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_role)))
                .andExpect(status().isOk());

        // Validate the Server_role in the database
        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeUpdate);
        Server_role testServer_role = server_roles.get(server_roles.size() - 1);
        assertThat(testServer_role.getServer_role_name()).isEqualTo(UPDATED_SERVER_ROLE_NAME);
        assertThat(testServer_role.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServer_role.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testServer_role.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServer_role.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServer_role.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServer_role.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteServer_role() throws Exception {
        // Initialize the database
        server_roleRepository.saveAndFlush(server_role);

		int databaseSizeBeforeDelete = server_roleRepository.findAll().size();

        // Get the server_role
        restServer_roleMockMvc.perform(delete("/api/server_roles/{id}", server_role.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Server_role> server_roles = server_roleRepository.findAll();
        assertThat(server_roles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
