package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.App_type;
import com.asp.dgdtool.repository.App_typeRepository;
import com.asp.dgdtool.repository.search.App_typeSearchRepository;

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
 * Test class for the App_typeResource REST controller.
 *
 * @see App_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class App_typeResourceTest {

    private static final String DEFAULT_APP_TYPE_NAME = "AAAAA";
    private static final String UPDATED_APP_TYPE_NAME = "BBBBB";
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
    private App_typeRepository app_typeRepository;

    @Inject
    private App_typeSearchRepository app_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApp_typeMockMvc;

    private App_type app_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        App_typeResource app_typeResource = new App_typeResource();
        ReflectionTestUtils.setField(app_typeResource, "app_typeRepository", app_typeRepository);
        ReflectionTestUtils.setField(app_typeResource, "app_typeSearchRepository", app_typeSearchRepository);
        this.restApp_typeMockMvc = MockMvcBuilders.standaloneSetup(app_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        app_type = new App_type();
        app_type.setApp_type_name(DEFAULT_APP_TYPE_NAME);
        app_type.setDescription(DEFAULT_DESCRIPTION);
        app_type.setStatus_id(DEFAULT_STATUS_ID);
        app_type.setCreated_by(DEFAULT_CREATED_BY);
        app_type.setCreated_date(DEFAULT_CREATED_DATE);
        app_type.setModified_by(DEFAULT_MODIFIED_BY);
        app_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApp_type() throws Exception {
        int databaseSizeBeforeCreate = app_typeRepository.findAll().size();

        // Create the App_type

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isCreated());

        // Validate the App_type in the database
        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeCreate + 1);
        App_type testApp_type = app_types.get(app_types.size() - 1);
        assertThat(testApp_type.getApp_type_name()).isEqualTo(DEFAULT_APP_TYPE_NAME);
        assertThat(testApp_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApp_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testApp_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApp_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApp_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApp_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkApp_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setApp_type_name(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setDescription(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setStatus_id(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setCreated_by(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setCreated_date(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setModified_by(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = app_typeRepository.findAll().size();
        // set the field null
        app_type.setModified_date(null);

        // Create the App_type, which fails.

        restApp_typeMockMvc.perform(post("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isBadRequest());

        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApp_types() throws Exception {
        // Initialize the database
        app_typeRepository.saveAndFlush(app_type);

        // Get all the app_types
        restApp_typeMockMvc.perform(get("/api/app_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(app_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].app_type_name").value(hasItem(DEFAULT_APP_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getApp_type() throws Exception {
        // Initialize the database
        app_typeRepository.saveAndFlush(app_type);

        // Get the app_type
        restApp_typeMockMvc.perform(get("/api/app_types/{id}", app_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(app_type.getId().intValue()))
            .andExpect(jsonPath("$.app_type_name").value(DEFAULT_APP_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApp_type() throws Exception {
        // Get the app_type
        restApp_typeMockMvc.perform(get("/api/app_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApp_type() throws Exception {
        // Initialize the database
        app_typeRepository.saveAndFlush(app_type);

		int databaseSizeBeforeUpdate = app_typeRepository.findAll().size();

        // Update the app_type
        app_type.setApp_type_name(UPDATED_APP_TYPE_NAME);
        app_type.setDescription(UPDATED_DESCRIPTION);
        app_type.setStatus_id(UPDATED_STATUS_ID);
        app_type.setCreated_by(UPDATED_CREATED_BY);
        app_type.setCreated_date(UPDATED_CREATED_DATE);
        app_type.setModified_by(UPDATED_MODIFIED_BY);
        app_type.setModified_date(UPDATED_MODIFIED_DATE);

        restApp_typeMockMvc.perform(put("/api/app_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app_type)))
                .andExpect(status().isOk());

        // Validate the App_type in the database
        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeUpdate);
        App_type testApp_type = app_types.get(app_types.size() - 1);
        assertThat(testApp_type.getApp_type_name()).isEqualTo(UPDATED_APP_TYPE_NAME);
        assertThat(testApp_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApp_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testApp_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApp_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApp_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApp_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteApp_type() throws Exception {
        // Initialize the database
        app_typeRepository.saveAndFlush(app_type);

		int databaseSizeBeforeDelete = app_typeRepository.findAll().size();

        // Get the app_type
        restApp_typeMockMvc.perform(delete("/api/app_types/{id}", app_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<App_type> app_types = app_typeRepository.findAll();
        assertThat(app_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
