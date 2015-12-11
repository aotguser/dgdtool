package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Frequency;
import com.asp.dgdtool.repository.FrequencyRepository;
import com.asp.dgdtool.repository.search.FrequencySearchRepository;

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
 * Test class for the FrequencyResource REST controller.
 *
 * @see FrequencyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FrequencyResourceTest {

    private static final String DEFAULT_FREQUENCY_NAME = "AAAAA";
    private static final String UPDATED_FREQUENCY_NAME = "BBBBB";
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
    private FrequencyRepository frequencyRepository;

    @Inject
    private FrequencySearchRepository frequencySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFrequencyMockMvc;

    private Frequency frequency;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FrequencyResource frequencyResource = new FrequencyResource();
        ReflectionTestUtils.setField(frequencyResource, "frequencyRepository", frequencyRepository);
        ReflectionTestUtils.setField(frequencyResource, "frequencySearchRepository", frequencySearchRepository);
        this.restFrequencyMockMvc = MockMvcBuilders.standaloneSetup(frequencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        frequency = new Frequency();
        frequency.setFrequency_name(DEFAULT_FREQUENCY_NAME);
        frequency.setDescription(DEFAULT_DESCRIPTION);
        frequency.setStatus_id(DEFAULT_STATUS_ID);
        frequency.setCreated_by(DEFAULT_CREATED_BY);
        frequency.setCreated_date(DEFAULT_CREATED_DATE);
        frequency.setModified_by(DEFAULT_MODIFIED_BY);
        frequency.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createFrequency() throws Exception {
        int databaseSizeBeforeCreate = frequencyRepository.findAll().size();

        // Create the Frequency

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isCreated());

        // Validate the Frequency in the database
        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeCreate + 1);
        Frequency testFrequency = frequencys.get(frequencys.size() - 1);
        assertThat(testFrequency.getFrequency_name()).isEqualTo(DEFAULT_FREQUENCY_NAME);
        assertThat(testFrequency.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFrequency.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testFrequency.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFrequency.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFrequency.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFrequency.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkFrequency_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setFrequency_name(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setDescription(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setStatus_id(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setCreated_by(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setCreated_date(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setModified_by(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = frequencyRepository.findAll().size();
        // set the field null
        frequency.setModified_date(null);

        // Create the Frequency, which fails.

        restFrequencyMockMvc.perform(post("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isBadRequest());

        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFrequencys() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        // Get all the frequencys
        restFrequencyMockMvc.perform(get("/api/frequencys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(frequency.getId().intValue())))
                .andExpect(jsonPath("$.[*].frequency_name").value(hasItem(DEFAULT_FREQUENCY_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFrequency() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

        // Get the frequency
        restFrequencyMockMvc.perform(get("/api/frequencys/{id}", frequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(frequency.getId().intValue()))
            .andExpect(jsonPath("$.frequency_name").value(DEFAULT_FREQUENCY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFrequency() throws Exception {
        // Get the frequency
        restFrequencyMockMvc.perform(get("/api/frequencys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFrequency() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

		int databaseSizeBeforeUpdate = frequencyRepository.findAll().size();

        // Update the frequency
        frequency.setFrequency_name(UPDATED_FREQUENCY_NAME);
        frequency.setDescription(UPDATED_DESCRIPTION);
        frequency.setStatus_id(UPDATED_STATUS_ID);
        frequency.setCreated_by(UPDATED_CREATED_BY);
        frequency.setCreated_date(UPDATED_CREATED_DATE);
        frequency.setModified_by(UPDATED_MODIFIED_BY);
        frequency.setModified_date(UPDATED_MODIFIED_DATE);

        restFrequencyMockMvc.perform(put("/api/frequencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(frequency)))
                .andExpect(status().isOk());

        // Validate the Frequency in the database
        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeUpdate);
        Frequency testFrequency = frequencys.get(frequencys.size() - 1);
        assertThat(testFrequency.getFrequency_name()).isEqualTo(UPDATED_FREQUENCY_NAME);
        assertThat(testFrequency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFrequency.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testFrequency.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFrequency.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFrequency.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFrequency.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteFrequency() throws Exception {
        // Initialize the database
        frequencyRepository.saveAndFlush(frequency);

		int databaseSizeBeforeDelete = frequencyRepository.findAll().size();

        // Get the frequency
        restFrequencyMockMvc.perform(delete("/api/frequencys/{id}", frequency.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Frequency> frequencys = frequencyRepository.findAll();
        assertThat(frequencys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
