package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.App;
import com.asp.dgdtool.repository.AppRepository;
import com.asp.dgdtool.repository.search.AppSearchRepository;

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
 * Test class for the AppResource REST controller.
 *
 * @see AppResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AppResourceTest {

    private static final String DEFAULT_APP_NAME = "AAAAA";
    private static final String UPDATED_APP_NAME = "BBBBB";

    private static final Integer DEFAULT_APP_TYPE_ID = 1;
    private static final Integer UPDATED_APP_TYPE_ID = 2;
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
    private AppRepository appRepository;

    @Inject
    private AppSearchRepository appSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAppMockMvc;

    private App app;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppResource appResource = new AppResource();
        ReflectionTestUtils.setField(appResource, "appRepository", appRepository);
        ReflectionTestUtils.setField(appResource, "appSearchRepository", appSearchRepository);
        this.restAppMockMvc = MockMvcBuilders.standaloneSetup(appResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        app = new App();
        app.setApp_name(DEFAULT_APP_NAME);
        app.setApp_type_id(DEFAULT_APP_TYPE_ID);
        app.setDescription(DEFAULT_DESCRIPTION);
        app.setStatus_id(DEFAULT_STATUS_ID);
        app.setCreated_by(DEFAULT_CREATED_BY);
        app.setCreated_date(DEFAULT_CREATED_DATE);
        app.setModified_by(DEFAULT_MODIFIED_BY);
        app.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApp() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();

        // Create the App

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isCreated());

        // Validate the App in the database
        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeCreate + 1);
        App testApp = apps.get(apps.size() - 1);
        assertThat(testApp.getApp_name()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(testApp.getApp_type_id()).isEqualTo(DEFAULT_APP_TYPE_ID);
        assertThat(testApp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApp.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testApp.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApp.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApp.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testApp.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkApp_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setApp_name(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApp_type_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setApp_type_id(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setDescription(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setStatus_id(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setCreated_by(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setCreated_date(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setModified_by(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRepository.findAll().size();
        // set the field null
        app.setModified_date(null);

        // Create the App, which fails.

        restAppMockMvc.perform(post("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isBadRequest());

        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApps() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get all the apps
        restAppMockMvc.perform(get("/api/apps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(app.getId().intValue())))
                .andExpect(jsonPath("$.[*].app_name").value(hasItem(DEFAULT_APP_NAME.toString())))
                .andExpect(jsonPath("$.[*].app_type_id").value(hasItem(DEFAULT_APP_TYPE_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", app.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(app.getId().intValue()))
            .andExpect(jsonPath("$.app_name").value(DEFAULT_APP_NAME.toString()))
            .andExpect(jsonPath("$.app_type_id").value(DEFAULT_APP_TYPE_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApp() throws Exception {
        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

		int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // Update the app
        app.setApp_name(UPDATED_APP_NAME);
        app.setApp_type_id(UPDATED_APP_TYPE_ID);
        app.setDescription(UPDATED_DESCRIPTION);
        app.setStatus_id(UPDATED_STATUS_ID);
        app.setCreated_by(UPDATED_CREATED_BY);
        app.setCreated_date(UPDATED_CREATED_DATE);
        app.setModified_by(UPDATED_MODIFIED_BY);
        app.setModified_date(UPDATED_MODIFIED_DATE);

        restAppMockMvc.perform(put("/api/apps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(app)))
                .andExpect(status().isOk());

        // Validate the App in the database
        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeUpdate);
        App testApp = apps.get(apps.size() - 1);
        assertThat(testApp.getApp_name()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testApp.getApp_type_id()).isEqualTo(UPDATED_APP_TYPE_ID);
        assertThat(testApp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApp.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testApp.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApp.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApp.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testApp.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

		int databaseSizeBeforeDelete = appRepository.findAll().size();

        // Get the app
        restAppMockMvc.perform(delete("/api/apps/{id}", app.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<App> apps = appRepository.findAll();
        assertThat(apps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
