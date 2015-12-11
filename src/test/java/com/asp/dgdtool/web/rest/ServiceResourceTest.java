package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Service;
import com.asp.dgdtool.repository.ServiceRepository;
import com.asp.dgdtool.repository.search.ServiceSearchRepository;

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
 * Test class for the ServiceResource REST controller.
 *
 * @see ServiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ServiceResourceTest {

    private static final String DEFAULT_SERVICE_NAME = "AAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBB";
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
    private ServiceRepository serviceRepository;

    @Inject
    private ServiceSearchRepository serviceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServiceMockMvc;

    private Service service;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceResource serviceResource = new ServiceResource();
        ReflectionTestUtils.setField(serviceResource, "serviceRepository", serviceRepository);
        ReflectionTestUtils.setField(serviceResource, "serviceSearchRepository", serviceSearchRepository);
        this.restServiceMockMvc = MockMvcBuilders.standaloneSetup(serviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        service = new Service();
        service.setService_name(DEFAULT_SERVICE_NAME);
        service.setDescription(DEFAULT_DESCRIPTION);
        service.setStatus_id(DEFAULT_STATUS_ID);
        service.setCreated_by(DEFAULT_CREATED_BY);
        service.setCreated_date(DEFAULT_CREATED_DATE);
        service.setModified_by(DEFAULT_MODIFIED_BY);
        service.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createService() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // Create the Service

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isCreated());

        // Validate the Service in the database
        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeCreate + 1);
        Service testService = services.get(services.size() - 1);
        assertThat(testService.getService_name()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testService.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testService.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testService.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testService.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testService.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkService_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setService_name(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setDescription(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setStatus_id(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setCreated_by(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setCreated_date(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setModified_by(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRepository.findAll().size();
        // set the field null
        service.setModified_date(null);

        // Create the Service, which fails.

        restServiceMockMvc.perform(post("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isBadRequest());

        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServices() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the services
        restServiceMockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
                .andExpect(jsonPath("$.[*].service_name").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(service.getId().intValue()))
            .andExpect(jsonPath("$.service_name").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

		int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service
        service.setService_name(UPDATED_SERVICE_NAME);
        service.setDescription(UPDATED_DESCRIPTION);
        service.setStatus_id(UPDATED_STATUS_ID);
        service.setCreated_by(UPDATED_CREATED_BY);
        service.setCreated_date(UPDATED_CREATED_DATE);
        service.setModified_by(UPDATED_MODIFIED_BY);
        service.setModified_date(UPDATED_MODIFIED_DATE);

        restServiceMockMvc.perform(put("/api/services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service)))
                .andExpect(status().isOk());

        // Validate the Service in the database
        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeUpdate);
        Service testService = services.get(services.size() - 1);
        assertThat(testService.getService_name()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testService.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testService.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testService.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testService.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testService.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

		int databaseSizeBeforeDelete = serviceRepository.findAll().size();

        // Get the service
        restServiceMockMvc.perform(delete("/api/services/{id}", service.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Service> services = serviceRepository.findAll();
        assertThat(services).hasSize(databaseSizeBeforeDelete - 1);
    }
}
