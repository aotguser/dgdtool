package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Feedback;
import com.asp.dgdtool.repository.FeedbackRepository;
import com.asp.dgdtool.repository.search.FeedbackSearchRepository;

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
 * Test class for the FeedbackResource REST controller.
 *
 * @see FeedbackResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeedbackResourceTest {


    private static final Integer DEFAULT_FEEDBACK_TYPE_ID = 1;
    private static final Integer UPDATED_FEEDBACK_TYPE_ID = 2;
    private static final String DEFAULT_FEEDBACK_NAME = "AAAAA";
    private static final String UPDATED_FEEDBACK_NAME = "BBBBB";
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
    private FeedbackRepository feedbackRepository;

    @Inject
    private FeedbackSearchRepository feedbackSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeedbackResource feedbackResource = new FeedbackResource();
        ReflectionTestUtils.setField(feedbackResource, "feedbackRepository", feedbackRepository);
        ReflectionTestUtils.setField(feedbackResource, "feedbackSearchRepository", feedbackSearchRepository);
        this.restFeedbackMockMvc = MockMvcBuilders.standaloneSetup(feedbackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feedback = new Feedback();
        feedback.setFeedback_type_id(DEFAULT_FEEDBACK_TYPE_ID);
        feedback.setFeedback_name(DEFAULT_FEEDBACK_NAME);
        feedback.setDescription(DEFAULT_DESCRIPTION);
        feedback.setStatus_id(DEFAULT_STATUS_ID);
        feedback.setCreated_by(DEFAULT_CREATED_BY);
        feedback.setCreated_date(DEFAULT_CREATED_DATE);
        feedback.setModified_by(DEFAULT_MODIFIED_BY);
        feedback.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbacks.get(feedbacks.size() - 1);
        assertThat(testFeedback.getFeedback_type_id()).isEqualTo(DEFAULT_FEEDBACK_TYPE_ID);
        assertThat(testFeedback.getFeedback_name()).isEqualTo(DEFAULT_FEEDBACK_NAME);
        assertThat(testFeedback.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeedback.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testFeedback.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFeedback.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFeedback.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFeedback.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkFeedback_type_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setFeedback_type_id(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeedback_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setFeedback_name(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setDescription(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setStatus_id(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setCreated_by(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setCreated_date(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setModified_by(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setModified_date(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isBadRequest());

        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbacks
        restFeedbackMockMvc.perform(get("/api/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
                .andExpect(jsonPath("$.[*].feedback_type_id").value(hasItem(DEFAULT_FEEDBACK_TYPE_ID)))
                .andExpect(jsonPath("$.[*].feedback_name").value(hasItem(DEFAULT_FEEDBACK_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.feedback_type_id").value(DEFAULT_FEEDBACK_TYPE_ID))
            .andExpect(jsonPath("$.feedback_name").value(DEFAULT_FEEDBACK_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

		int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        feedback.setFeedback_type_id(UPDATED_FEEDBACK_TYPE_ID);
        feedback.setFeedback_name(UPDATED_FEEDBACK_NAME);
        feedback.setDescription(UPDATED_DESCRIPTION);
        feedback.setStatus_id(UPDATED_STATUS_ID);
        feedback.setCreated_by(UPDATED_CREATED_BY);
        feedback.setCreated_date(UPDATED_CREATED_DATE);
        feedback.setModified_by(UPDATED_MODIFIED_BY);
        feedback.setModified_date(UPDATED_MODIFIED_DATE);

        restFeedbackMockMvc.perform(put("/api/feedbacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feedback)))
                .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbacks.get(feedbacks.size() - 1);
        assertThat(testFeedback.getFeedback_type_id()).isEqualTo(UPDATED_FEEDBACK_TYPE_ID);
        assertThat(testFeedback.getFeedback_name()).isEqualTo(UPDATED_FEEDBACK_NAME);
        assertThat(testFeedback.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeedback.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testFeedback.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFeedback.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFeedback.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFeedback.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

		int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Get the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
