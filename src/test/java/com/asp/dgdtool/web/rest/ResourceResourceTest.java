package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Resource;
import com.asp.dgdtool.repository.ResourceRepository;
import com.asp.dgdtool.repository.search.ResourceSearchRepository;

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
 * Test class for the ResourceResource REST controller.
 *
 * @see ResourceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResourceResourceTest {

    private static final String DEFAULT_RESOURCE_NAME = "AAAAA";
    private static final String UPDATED_RESOURCE_NAME = "BBBBB";
    private static final String DEFAULT_RESOURCE_NUMBER = "AAAAA";
    private static final String UPDATED_RESOURCE_NUMBER = "BBBBB";
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
    private ResourceRepository resourceRepository;

    @Inject
    private ResourceSearchRepository resourceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResourceMockMvc;

    private Resource resource;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResourceResource resourceResource = new ResourceResource();
        ReflectionTestUtils.setField(resourceResource, "resourceRepository", resourceRepository);
        ReflectionTestUtils.setField(resourceResource, "resourceSearchRepository", resourceSearchRepository);
        this.restResourceMockMvc = MockMvcBuilders.standaloneSetup(resourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resource = new Resource();
        resource.setResource_name(DEFAULT_RESOURCE_NAME);
        resource.setResource_number(DEFAULT_RESOURCE_NUMBER);
        resource.setDescription(DEFAULT_DESCRIPTION);
        resource.setStatus_id(DEFAULT_STATUS_ID);
        resource.setCreated_by(DEFAULT_CREATED_BY);
        resource.setCreated_date(DEFAULT_CREATED_DATE);
        resource.setModified_by(DEFAULT_MODIFIED_BY);
        resource.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeCreate + 1);
        Resource testResource = resources.get(resources.size() - 1);
        assertThat(testResource.getResource_name()).isEqualTo(DEFAULT_RESOURCE_NAME);
        assertThat(testResource.getResource_number()).isEqualTo(DEFAULT_RESOURCE_NUMBER);
        assertThat(testResource.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResource.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testResource.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResource.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testResource.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testResource.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkResource_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setResource_name(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResource_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setResource_number(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setDescription(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setStatus_id(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setCreated_by(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setCreated_date(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setModified_by(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setModified_date(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isBadRequest());

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resources
        restResourceMockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
                .andExpect(jsonPath("$.[*].resource_name").value(hasItem(DEFAULT_RESOURCE_NAME.toString())))
                .andExpect(jsonPath("$.[*].resource_number").value(hasItem(DEFAULT_RESOURCE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", resource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resource.getId().intValue()))
            .andExpect(jsonPath("$.resource_name").value(DEFAULT_RESOURCE_NAME.toString()))
            .andExpect(jsonPath("$.resource_number").value(DEFAULT_RESOURCE_NUMBER.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResource() throws Exception {
        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

		int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource
        resource.setResource_name(UPDATED_RESOURCE_NAME);
        resource.setResource_number(UPDATED_RESOURCE_NUMBER);
        resource.setDescription(UPDATED_DESCRIPTION);
        resource.setStatus_id(UPDATED_STATUS_ID);
        resource.setCreated_by(UPDATED_CREATED_BY);
        resource.setCreated_date(UPDATED_CREATED_DATE);
        resource.setModified_by(UPDATED_MODIFIED_BY);
        resource.setModified_date(UPDATED_MODIFIED_DATE);

        restResourceMockMvc.perform(put("/api/resources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resource)))
                .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resources.get(resources.size() - 1);
        assertThat(testResource.getResource_name()).isEqualTo(UPDATED_RESOURCE_NAME);
        assertThat(testResource.getResource_number()).isEqualTo(UPDATED_RESOURCE_NUMBER);
        assertThat(testResource.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResource.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testResource.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResource.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testResource.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testResource.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

		int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Get the resource
        restResourceMockMvc.perform(delete("/api/resources/{id}", resource.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(databaseSizeBeforeDelete - 1);
    }
}
