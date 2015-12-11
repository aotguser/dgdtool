package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Os_type;
import com.asp.dgdtool.repository.Os_typeRepository;
import com.asp.dgdtool.repository.search.Os_typeSearchRepository;

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
 * Test class for the Os_typeResource REST controller.
 *
 * @see Os_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Os_typeResourceTest {

    private static final String DEFAULT_OS_TYPE_NAME = "AAAAA";
    private static final String UPDATED_OS_TYPE_NAME = "BBBBB";
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
    private Os_typeRepository os_typeRepository;

    @Inject
    private Os_typeSearchRepository os_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOs_typeMockMvc;

    private Os_type os_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Os_typeResource os_typeResource = new Os_typeResource();
        ReflectionTestUtils.setField(os_typeResource, "os_typeRepository", os_typeRepository);
        ReflectionTestUtils.setField(os_typeResource, "os_typeSearchRepository", os_typeSearchRepository);
        this.restOs_typeMockMvc = MockMvcBuilders.standaloneSetup(os_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        os_type = new Os_type();
        os_type.setOs_type_name(DEFAULT_OS_TYPE_NAME);
        os_type.setDescription(DEFAULT_DESCRIPTION);
        os_type.setStatus_id(DEFAULT_STATUS_ID);
        os_type.setCreated_by(DEFAULT_CREATED_BY);
        os_type.setCreated_date(DEFAULT_CREATED_DATE);
        os_type.setModified_by(DEFAULT_MODIFIED_BY);
        os_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createOs_type() throws Exception {
        int databaseSizeBeforeCreate = os_typeRepository.findAll().size();

        // Create the Os_type

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isCreated());

        // Validate the Os_type in the database
        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeCreate + 1);
        Os_type testOs_type = os_types.get(os_types.size() - 1);
        assertThat(testOs_type.getOs_type_name()).isEqualTo(DEFAULT_OS_TYPE_NAME);
        assertThat(testOs_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOs_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testOs_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOs_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOs_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testOs_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkOs_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setOs_type_name(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setDescription(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setStatus_id(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setCreated_by(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setCreated_date(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setModified_by(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_typeRepository.findAll().size();
        // set the field null
        os_type.setModified_date(null);

        // Create the Os_type, which fails.

        restOs_typeMockMvc.perform(post("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isBadRequest());

        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOs_types() throws Exception {
        // Initialize the database
        os_typeRepository.saveAndFlush(os_type);

        // Get all the os_types
        restOs_typeMockMvc.perform(get("/api/os_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(os_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].os_type_name").value(hasItem(DEFAULT_OS_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getOs_type() throws Exception {
        // Initialize the database
        os_typeRepository.saveAndFlush(os_type);

        // Get the os_type
        restOs_typeMockMvc.perform(get("/api/os_types/{id}", os_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(os_type.getId().intValue()))
            .andExpect(jsonPath("$.os_type_name").value(DEFAULT_OS_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOs_type() throws Exception {
        // Get the os_type
        restOs_typeMockMvc.perform(get("/api/os_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOs_type() throws Exception {
        // Initialize the database
        os_typeRepository.saveAndFlush(os_type);

		int databaseSizeBeforeUpdate = os_typeRepository.findAll().size();

        // Update the os_type
        os_type.setOs_type_name(UPDATED_OS_TYPE_NAME);
        os_type.setDescription(UPDATED_DESCRIPTION);
        os_type.setStatus_id(UPDATED_STATUS_ID);
        os_type.setCreated_by(UPDATED_CREATED_BY);
        os_type.setCreated_date(UPDATED_CREATED_DATE);
        os_type.setModified_by(UPDATED_MODIFIED_BY);
        os_type.setModified_date(UPDATED_MODIFIED_DATE);

        restOs_typeMockMvc.perform(put("/api/os_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_type)))
                .andExpect(status().isOk());

        // Validate the Os_type in the database
        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeUpdate);
        Os_type testOs_type = os_types.get(os_types.size() - 1);
        assertThat(testOs_type.getOs_type_name()).isEqualTo(UPDATED_OS_TYPE_NAME);
        assertThat(testOs_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOs_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testOs_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOs_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOs_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testOs_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteOs_type() throws Exception {
        // Initialize the database
        os_typeRepository.saveAndFlush(os_type);

		int databaseSizeBeforeDelete = os_typeRepository.findAll().size();

        // Get the os_type
        restOs_typeMockMvc.perform(delete("/api/os_types/{id}", os_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Os_type> os_types = os_typeRepository.findAll();
        assertThat(os_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
