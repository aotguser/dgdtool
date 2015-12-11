package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Stage;
import com.asp.dgdtool.repository.StageRepository;
import com.asp.dgdtool.repository.search.StageSearchRepository;

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
 * Test class for the StageResource REST controller.
 *
 * @see StageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StageResourceTest {

    private static final String DEFAULT_STAGE_NAME = "AAAAA";
    private static final String UPDATED_STAGE_NAME = "BBBBB";
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
    private StageRepository stageRepository;

    @Inject
    private StageSearchRepository stageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStageMockMvc;

    private Stage stage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StageResource stageResource = new StageResource();
        ReflectionTestUtils.setField(stageResource, "stageRepository", stageRepository);
        ReflectionTestUtils.setField(stageResource, "stageSearchRepository", stageSearchRepository);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stage = new Stage();
        stage.setStage_name(DEFAULT_STAGE_NAME);
        stage.setDescription(DEFAULT_DESCRIPTION);
        stage.setStatus_id(DEFAULT_STATUS_ID);
        stage.setCreated_by(DEFAULT_CREATED_BY);
        stage.setCreated_date(DEFAULT_CREATED_DATE);
        stage.setModified_by(DEFAULT_MODIFIED_BY);
        stage.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getStage_name()).isEqualTo(DEFAULT_STAGE_NAME);
        assertThat(testStage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStage.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testStage.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStage.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStage.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testStage.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkStage_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setStage_name(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setDescription(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setStatus_id(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setCreated_by(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setCreated_date(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setModified_by(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setModified_date(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stages
        restStageMockMvc.perform(get("/api/stages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
                .andExpect(jsonPath("$.[*].stage_name").value(hasItem(DEFAULT_STAGE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.stage_name").value(DEFAULT_STAGE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

		int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        stage.setStage_name(UPDATED_STAGE_NAME);
        stage.setDescription(UPDATED_DESCRIPTION);
        stage.setStatus_id(UPDATED_STATUS_ID);
        stage.setCreated_by(UPDATED_CREATED_BY);
        stage.setCreated_date(UPDATED_CREATED_DATE);
        stage.setModified_by(UPDATED_MODIFIED_BY);
        stage.setModified_date(UPDATED_MODIFIED_DATE);

        restStageMockMvc.perform(put("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getStage_name()).isEqualTo(UPDATED_STAGE_NAME);
        assertThat(testStage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStage.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testStage.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStage.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStage.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testStage.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

		int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Get the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
