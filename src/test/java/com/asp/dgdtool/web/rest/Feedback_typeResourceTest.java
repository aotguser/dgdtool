package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Feedback_type;
import com.asp.dgdtool.repository.Feedback_typeRepository;
import com.asp.dgdtool.repository.search.Feedback_typeSearchRepository;

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
 * Test class for the Feedback_typeResource REST controller.
 *
 * @see Feedback_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Feedback_typeResourceTest {

    private static final String DEFAULT_FEEDBACK_TYPE_NAME = "AAAAA";
    private static final String UPDATED_FEEDBACK_TYPE_NAME = "BBBBB";
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
    private Feedback_typeRepository feedback_typeRepository;

    @Inject
    private Feedback_typeSearchRepository feedback_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeedback_typeMockMvc;

    private Feedback_type feedback_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Feedback_typeResource feedback_typeResource = new Feedback_typeResource();
        ReflectionTestUtils.setField(feedback_typeResource, "feedback_typeRepository", feedback_typeRepository);
        ReflectionTestUtils.setField(feedback_typeResource, "feedback_typeSearchRepository", feedback_typeSearchRepository);
        this.restFeedback_typeMockMvc = MockMvcBuilders.standaloneSetup(feedback_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feedback_type = new Feedback_type();
        feedback_type.setFeedback_type_name(DEFAULT_FEEDBACK_TYPE_NAME);
        feedback_type.setDescription(DEFAULT_DESCRIPTION);
        feedback_type.setStatus_id(DEFAULT_STATUS_ID);
        feedback_type.setCreated_by(DEFAULT_CREATED_BY);
        feedback_type.setCreated_date(DEFAULT_CREATED_DATE);
        feedback_type.setModified_by(DEFAULT_MODIFIED_BY);
        feedback_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createFeedback_type() throws Exception {
        int databaseSizeBeforeCreate = feedback_typeRepository.findAll().size();

        // Create the Feedback_type

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isCreated());

        // Validate the Feedback_type in the database
        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeCreate + 1);
        Feedback_type testFeedback_type = feedback_types.get(feedback_types.size() - 1);
        assertThat(testFeedback_type.getFeedback_type_name()).isEqualTo(DEFAULT_FEEDBACK_TYPE_NAME);
        assertThat(testFeedback_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeedback_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testFeedback_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFeedback_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFeedback_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFeedback_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkFeedback_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setFeedback_type_name(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setDescription(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setStatus_id(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setCreated_by(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setCreated_date(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setModified_by(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedback_typeRepository.findAll().size();
        // set the field null
        feedback_type.setModified_date(null);

        // Create the Feedback_type, which fails.

        restFeedback_typeMockMvc.perform(post("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isBadRequest());

        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedback_types() throws Exception {
        // Initialize the database
        feedback_typeRepository.saveAndFlush(feedback_type);

        // Get all the feedback_types
        restFeedback_typeMockMvc.perform(get("/api/feedback_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feedback_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].feedback_type_name").value(hasItem(DEFAULT_FEEDBACK_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFeedback_type() throws Exception {
        // Initialize the database
        feedback_typeRepository.saveAndFlush(feedback_type);

        // Get the feedback_type
        restFeedback_typeMockMvc.perform(get("/api/feedback_types/{id}", feedback_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feedback_type.getId().intValue()))
            .andExpect(jsonPath("$.feedback_type_name").value(DEFAULT_FEEDBACK_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeedback_type() throws Exception {
        // Get the feedback_type
        restFeedback_typeMockMvc.perform(get("/api/feedback_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback_type() throws Exception {
        // Initialize the database
        feedback_typeRepository.saveAndFlush(feedback_type);

		int databaseSizeBeforeUpdate = feedback_typeRepository.findAll().size();

        // Update the feedback_type
        feedback_type.setFeedback_type_name(UPDATED_FEEDBACK_TYPE_NAME);
        feedback_type.setDescription(UPDATED_DESCRIPTION);
        feedback_type.setStatus_id(UPDATED_STATUS_ID);
        feedback_type.setCreated_by(UPDATED_CREATED_BY);
        feedback_type.setCreated_date(UPDATED_CREATED_DATE);
        feedback_type.setModified_by(UPDATED_MODIFIED_BY);
        feedback_type.setModified_date(UPDATED_MODIFIED_DATE);

        restFeedback_typeMockMvc.perform(put("/api/feedback_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback_type)))
                .andExpect(status().isOk());

        // Validate the Feedback_type in the database
        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeUpdate);
        Feedback_type testFeedback_type = feedback_types.get(feedback_types.size() - 1);
        assertThat(testFeedback_type.getFeedback_type_name()).isEqualTo(UPDATED_FEEDBACK_TYPE_NAME);
        assertThat(testFeedback_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeedback_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testFeedback_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFeedback_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFeedback_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFeedback_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteFeedback_type() throws Exception {
        // Initialize the database
        feedback_typeRepository.saveAndFlush(feedback_type);

		int databaseSizeBeforeDelete = feedback_typeRepository.findAll().size();

        // Get the feedback_type
        restFeedback_typeMockMvc.perform(delete("/api/feedback_types/{id}", feedback_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Feedback_type> feedback_types = feedback_typeRepository.findAll();
        assertThat(feedback_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
