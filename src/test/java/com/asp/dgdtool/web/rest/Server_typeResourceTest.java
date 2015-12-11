package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Server_type;
import com.asp.dgdtool.repository.Server_typeRepository;
import com.asp.dgdtool.repository.search.Server_typeSearchRepository;

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
 * Test class for the Server_typeResource REST controller.
 *
 * @see Server_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Server_typeResourceTest {

    private static final String DEFAULT_SERVER_TYPE_NAME = "AAAAA";
    private static final String UPDATED_SERVER_TYPE_NAME = "BBBBB";
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
    private Server_typeRepository server_typeRepository;

    @Inject
    private Server_typeSearchRepository server_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServer_typeMockMvc;

    private Server_type server_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Server_typeResource server_typeResource = new Server_typeResource();
        ReflectionTestUtils.setField(server_typeResource, "server_typeRepository", server_typeRepository);
        ReflectionTestUtils.setField(server_typeResource, "server_typeSearchRepository", server_typeSearchRepository);
        this.restServer_typeMockMvc = MockMvcBuilders.standaloneSetup(server_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        server_type = new Server_type();
        server_type.setServer_type_name(DEFAULT_SERVER_TYPE_NAME);
        server_type.setDescription(DEFAULT_DESCRIPTION);
        server_type.setStatus_id(DEFAULT_STATUS_ID);
        server_type.setCreated_by(DEFAULT_CREATED_BY);
        server_type.setCreated_date(DEFAULT_CREATED_DATE);
        server_type.setModified_by(DEFAULT_MODIFIED_BY);
        server_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createServer_type() throws Exception {
        int databaseSizeBeforeCreate = server_typeRepository.findAll().size();

        // Create the Server_type

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isCreated());

        // Validate the Server_type in the database
        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeCreate + 1);
        Server_type testServer_type = server_types.get(server_types.size() - 1);
        assertThat(testServer_type.getServer_type_name()).isEqualTo(DEFAULT_SERVER_TYPE_NAME);
        assertThat(testServer_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServer_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testServer_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServer_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testServer_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testServer_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkServer_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setServer_type_name(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setDescription(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setStatus_id(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setCreated_by(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setCreated_date(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setModified_by(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = server_typeRepository.findAll().size();
        // set the field null
        server_type.setModified_date(null);

        // Create the Server_type, which fails.

        restServer_typeMockMvc.perform(post("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isBadRequest());

        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServer_types() throws Exception {
        // Initialize the database
        server_typeRepository.saveAndFlush(server_type);

        // Get all the server_types
        restServer_typeMockMvc.perform(get("/api/server_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(server_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].server_type_name").value(hasItem(DEFAULT_SERVER_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getServer_type() throws Exception {
        // Initialize the database
        server_typeRepository.saveAndFlush(server_type);

        // Get the server_type
        restServer_typeMockMvc.perform(get("/api/server_types/{id}", server_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(server_type.getId().intValue()))
            .andExpect(jsonPath("$.server_type_name").value(DEFAULT_SERVER_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServer_type() throws Exception {
        // Get the server_type
        restServer_typeMockMvc.perform(get("/api/server_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServer_type() throws Exception {
        // Initialize the database
        server_typeRepository.saveAndFlush(server_type);

		int databaseSizeBeforeUpdate = server_typeRepository.findAll().size();

        // Update the server_type
        server_type.setServer_type_name(UPDATED_SERVER_TYPE_NAME);
        server_type.setDescription(UPDATED_DESCRIPTION);
        server_type.setStatus_id(UPDATED_STATUS_ID);
        server_type.setCreated_by(UPDATED_CREATED_BY);
        server_type.setCreated_date(UPDATED_CREATED_DATE);
        server_type.setModified_by(UPDATED_MODIFIED_BY);
        server_type.setModified_date(UPDATED_MODIFIED_DATE);

        restServer_typeMockMvc.perform(put("/api/server_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(server_type)))
                .andExpect(status().isOk());

        // Validate the Server_type in the database
        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeUpdate);
        Server_type testServer_type = server_types.get(server_types.size() - 1);
        assertThat(testServer_type.getServer_type_name()).isEqualTo(UPDATED_SERVER_TYPE_NAME);
        assertThat(testServer_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServer_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testServer_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServer_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testServer_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testServer_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteServer_type() throws Exception {
        // Initialize the database
        server_typeRepository.saveAndFlush(server_type);

		int databaseSizeBeforeDelete = server_typeRepository.findAll().size();

        // Get the server_type
        restServer_typeMockMvc.perform(delete("/api/server_types/{id}", server_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Server_type> server_types = server_typeRepository.findAll();
        assertThat(server_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
