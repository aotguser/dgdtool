package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.End_state_designation;
import com.asp.dgdtool.repository.End_state_designationRepository;
import com.asp.dgdtool.repository.search.End_state_designationSearchRepository;

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
 * Test class for the End_state_designationResource REST controller.
 *
 * @see End_state_designationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class End_state_designationResourceTest {

    private static final String DEFAULT_END_STATE_DESIGNATION_NAME = "AAAAA";
    private static final String UPDATED_END_STATE_DESIGNATION_NAME = "BBBBB";
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
    private End_state_designationRepository end_state_designationRepository;

    @Inject
    private End_state_designationSearchRepository end_state_designationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEnd_state_designationMockMvc;

    private End_state_designation end_state_designation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        End_state_designationResource end_state_designationResource = new End_state_designationResource();
        ReflectionTestUtils.setField(end_state_designationResource, "end_state_designationRepository", end_state_designationRepository);
        ReflectionTestUtils.setField(end_state_designationResource, "end_state_designationSearchRepository", end_state_designationSearchRepository);
        this.restEnd_state_designationMockMvc = MockMvcBuilders.standaloneSetup(end_state_designationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        end_state_designation = new End_state_designation();
        end_state_designation.setEnd_state_designation_name(DEFAULT_END_STATE_DESIGNATION_NAME);
        end_state_designation.setDescription(DEFAULT_DESCRIPTION);
        end_state_designation.setStatus_id(DEFAULT_STATUS_ID);
        end_state_designation.setCreated_by(DEFAULT_CREATED_BY);
        end_state_designation.setCreated_date(DEFAULT_CREATED_DATE);
        end_state_designation.setModified_by(DEFAULT_MODIFIED_BY);
        end_state_designation.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createEnd_state_designation() throws Exception {
        int databaseSizeBeforeCreate = end_state_designationRepository.findAll().size();

        // Create the End_state_designation

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isCreated());

        // Validate the End_state_designation in the database
        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeCreate + 1);
        End_state_designation testEnd_state_designation = end_state_designations.get(end_state_designations.size() - 1);
        assertThat(testEnd_state_designation.getEnd_state_designation_name()).isEqualTo(DEFAULT_END_STATE_DESIGNATION_NAME);
        assertThat(testEnd_state_designation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnd_state_designation.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testEnd_state_designation.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEnd_state_designation.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEnd_state_designation.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEnd_state_designation.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkEnd_state_designation_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setEnd_state_designation_name(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setDescription(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setStatus_id(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setCreated_by(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setCreated_date(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setModified_by(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = end_state_designationRepository.findAll().size();
        // set the field null
        end_state_designation.setModified_date(null);

        // Create the End_state_designation, which fails.

        restEnd_state_designationMockMvc.perform(post("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isBadRequest());

        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnd_state_designations() throws Exception {
        // Initialize the database
        end_state_designationRepository.saveAndFlush(end_state_designation);

        // Get all the end_state_designations
        restEnd_state_designationMockMvc.perform(get("/api/end_state_designations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(end_state_designation.getId().intValue())))
                .andExpect(jsonPath("$.[*].end_state_designation_name").value(hasItem(DEFAULT_END_STATE_DESIGNATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getEnd_state_designation() throws Exception {
        // Initialize the database
        end_state_designationRepository.saveAndFlush(end_state_designation);

        // Get the end_state_designation
        restEnd_state_designationMockMvc.perform(get("/api/end_state_designations/{id}", end_state_designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(end_state_designation.getId().intValue()))
            .andExpect(jsonPath("$.end_state_designation_name").value(DEFAULT_END_STATE_DESIGNATION_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnd_state_designation() throws Exception {
        // Get the end_state_designation
        restEnd_state_designationMockMvc.perform(get("/api/end_state_designations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnd_state_designation() throws Exception {
        // Initialize the database
        end_state_designationRepository.saveAndFlush(end_state_designation);

		int databaseSizeBeforeUpdate = end_state_designationRepository.findAll().size();

        // Update the end_state_designation
        end_state_designation.setEnd_state_designation_name(UPDATED_END_STATE_DESIGNATION_NAME);
        end_state_designation.setDescription(UPDATED_DESCRIPTION);
        end_state_designation.setStatus_id(UPDATED_STATUS_ID);
        end_state_designation.setCreated_by(UPDATED_CREATED_BY);
        end_state_designation.setCreated_date(UPDATED_CREATED_DATE);
        end_state_designation.setModified_by(UPDATED_MODIFIED_BY);
        end_state_designation.setModified_date(UPDATED_MODIFIED_DATE);

        restEnd_state_designationMockMvc.perform(put("/api/end_state_designations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(end_state_designation)))
                .andExpect(status().isOk());

        // Validate the End_state_designation in the database
        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeUpdate);
        End_state_designation testEnd_state_designation = end_state_designations.get(end_state_designations.size() - 1);
        assertThat(testEnd_state_designation.getEnd_state_designation_name()).isEqualTo(UPDATED_END_STATE_DESIGNATION_NAME);
        assertThat(testEnd_state_designation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnd_state_designation.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testEnd_state_designation.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEnd_state_designation.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEnd_state_designation.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEnd_state_designation.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteEnd_state_designation() throws Exception {
        // Initialize the database
        end_state_designationRepository.saveAndFlush(end_state_designation);

		int databaseSizeBeforeDelete = end_state_designationRepository.findAll().size();

        // Get the end_state_designation
        restEnd_state_designationMockMvc.perform(delete("/api/end_state_designations/{id}", end_state_designation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<End_state_designation> end_state_designations = end_state_designationRepository.findAll();
        assertThat(end_state_designations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
