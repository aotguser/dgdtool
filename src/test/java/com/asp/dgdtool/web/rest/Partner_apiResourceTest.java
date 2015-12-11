package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Partner_api;
import com.asp.dgdtool.repository.Partner_apiRepository;
import com.asp.dgdtool.repository.search.Partner_apiSearchRepository;

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
 * Test class for the Partner_apiResource REST controller.
 *
 * @see Partner_apiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Partner_apiResourceTest {


    private static final Integer DEFAULT_PARTNER_ID = 1;
    private static final Integer UPDATED_PARTNER_ID = 2;
    private static final String DEFAULT_PARTNER_REQ_URL = "AAAAA";
    private static final String UPDATED_PARTNER_REQ_URL = "BBBBB";
    private static final String DEFAULT_PARTNER_REQ_OBJ = "AAAAA";
    private static final String UPDATED_PARTNER_REQ_OBJ = "BBBBB";
    private static final String DEFAULT_PARTNER_RES_URL = "AAAAA";
    private static final String UPDATED_PARTNER_RES_URL = "BBBBB";
    private static final String DEFAULT_ASP_METHOD = "AAAAA";
    private static final String UPDATED_ASP_METHOD = "BBBBB";

    private static final Integer DEFAULT_ASP_APP_ID = 1;
    private static final Integer UPDATED_ASP_APP_ID = 2;
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
    private Partner_apiRepository partner_apiRepository;

    @Inject
    private Partner_apiSearchRepository partner_apiSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartner_apiMockMvc;

    private Partner_api partner_api;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Partner_apiResource partner_apiResource = new Partner_apiResource();
        ReflectionTestUtils.setField(partner_apiResource, "partner_apiRepository", partner_apiRepository);
        ReflectionTestUtils.setField(partner_apiResource, "partner_apiSearchRepository", partner_apiSearchRepository);
        this.restPartner_apiMockMvc = MockMvcBuilders.standaloneSetup(partner_apiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        partner_api = new Partner_api();
        partner_api.setPartner_id(DEFAULT_PARTNER_ID);
        partner_api.setPartner_req_url(DEFAULT_PARTNER_REQ_URL);
        partner_api.setPartner_req_obj(DEFAULT_PARTNER_REQ_OBJ);
        partner_api.setPartner_res_url(DEFAULT_PARTNER_RES_URL);
        partner_api.setAsp_method(DEFAULT_ASP_METHOD);
        partner_api.setAsp_app_id(DEFAULT_ASP_APP_ID);
        partner_api.setDescription(DEFAULT_DESCRIPTION);
        partner_api.setStatus_id(DEFAULT_STATUS_ID);
        partner_api.setCreated_by(DEFAULT_CREATED_BY);
        partner_api.setCreated_date(DEFAULT_CREATED_DATE);
        partner_api.setModified_by(DEFAULT_MODIFIED_BY);
        partner_api.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPartner_api() throws Exception {
        int databaseSizeBeforeCreate = partner_apiRepository.findAll().size();

        // Create the Partner_api

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isCreated());

        // Validate the Partner_api in the database
        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeCreate + 1);
        Partner_api testPartner_api = partner_apis.get(partner_apis.size() - 1);
        assertThat(testPartner_api.getPartner_id()).isEqualTo(DEFAULT_PARTNER_ID);
        assertThat(testPartner_api.getPartner_req_url()).isEqualTo(DEFAULT_PARTNER_REQ_URL);
        assertThat(testPartner_api.getPartner_req_obj()).isEqualTo(DEFAULT_PARTNER_REQ_OBJ);
        assertThat(testPartner_api.getPartner_res_url()).isEqualTo(DEFAULT_PARTNER_RES_URL);
        assertThat(testPartner_api.getAsp_method()).isEqualTo(DEFAULT_ASP_METHOD);
        assertThat(testPartner_api.getAsp_app_id()).isEqualTo(DEFAULT_ASP_APP_ID);
        assertThat(testPartner_api.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartner_api.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPartner_api.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartner_api.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPartner_api.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPartner_api.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPartner_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setPartner_id(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner_req_urlIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setPartner_req_url(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner_req_objIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setPartner_req_obj(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner_res_urlIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setPartner_res_url(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAsp_methodIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setAsp_method(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setDescription(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setStatus_id(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setCreated_by(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setModified_by(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partner_apiRepository.findAll().size();
        // set the field null
        partner_api.setModified_date(null);

        // Create the Partner_api, which fails.

        restPartner_apiMockMvc.perform(post("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isBadRequest());

        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartner_apis() throws Exception {
        // Initialize the database
        partner_apiRepository.saveAndFlush(partner_api);

        // Get all the partner_apis
        restPartner_apiMockMvc.perform(get("/api/partner_apis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partner_api.getId().intValue())))
                .andExpect(jsonPath("$.[*].partner_id").value(hasItem(DEFAULT_PARTNER_ID)))
                .andExpect(jsonPath("$.[*].partner_req_url").value(hasItem(DEFAULT_PARTNER_REQ_URL.toString())))
                .andExpect(jsonPath("$.[*].partner_req_obj").value(hasItem(DEFAULT_PARTNER_REQ_OBJ.toString())))
                .andExpect(jsonPath("$.[*].partner_res_url").value(hasItem(DEFAULT_PARTNER_RES_URL.toString())))
                .andExpect(jsonPath("$.[*].asp_method").value(hasItem(DEFAULT_ASP_METHOD.toString())))
                .andExpect(jsonPath("$.[*].asp_app_id").value(hasItem(DEFAULT_ASP_APP_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPartner_api() throws Exception {
        // Initialize the database
        partner_apiRepository.saveAndFlush(partner_api);

        // Get the partner_api
        restPartner_apiMockMvc.perform(get("/api/partner_apis/{id}", partner_api.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partner_api.getId().intValue()))
            .andExpect(jsonPath("$.partner_id").value(DEFAULT_PARTNER_ID))
            .andExpect(jsonPath("$.partner_req_url").value(DEFAULT_PARTNER_REQ_URL.toString()))
            .andExpect(jsonPath("$.partner_req_obj").value(DEFAULT_PARTNER_REQ_OBJ.toString()))
            .andExpect(jsonPath("$.partner_res_url").value(DEFAULT_PARTNER_RES_URL.toString()))
            .andExpect(jsonPath("$.asp_method").value(DEFAULT_ASP_METHOD.toString()))
            .andExpect(jsonPath("$.asp_app_id").value(DEFAULT_ASP_APP_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartner_api() throws Exception {
        // Get the partner_api
        restPartner_apiMockMvc.perform(get("/api/partner_apis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartner_api() throws Exception {
        // Initialize the database
        partner_apiRepository.saveAndFlush(partner_api);

		int databaseSizeBeforeUpdate = partner_apiRepository.findAll().size();

        // Update the partner_api
        partner_api.setPartner_id(UPDATED_PARTNER_ID);
        partner_api.setPartner_req_url(UPDATED_PARTNER_REQ_URL);
        partner_api.setPartner_req_obj(UPDATED_PARTNER_REQ_OBJ);
        partner_api.setPartner_res_url(UPDATED_PARTNER_RES_URL);
        partner_api.setAsp_method(UPDATED_ASP_METHOD);
        partner_api.setAsp_app_id(UPDATED_ASP_APP_ID);
        partner_api.setDescription(UPDATED_DESCRIPTION);
        partner_api.setStatus_id(UPDATED_STATUS_ID);
        partner_api.setCreated_by(UPDATED_CREATED_BY);
        partner_api.setCreated_date(UPDATED_CREATED_DATE);
        partner_api.setModified_by(UPDATED_MODIFIED_BY);
        partner_api.setModified_date(UPDATED_MODIFIED_DATE);

        restPartner_apiMockMvc.perform(put("/api/partner_apis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner_api)))
                .andExpect(status().isOk());

        // Validate the Partner_api in the database
        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeUpdate);
        Partner_api testPartner_api = partner_apis.get(partner_apis.size() - 1);
        assertThat(testPartner_api.getPartner_id()).isEqualTo(UPDATED_PARTNER_ID);
        assertThat(testPartner_api.getPartner_req_url()).isEqualTo(UPDATED_PARTNER_REQ_URL);
        assertThat(testPartner_api.getPartner_req_obj()).isEqualTo(UPDATED_PARTNER_REQ_OBJ);
        assertThat(testPartner_api.getPartner_res_url()).isEqualTo(UPDATED_PARTNER_RES_URL);
        assertThat(testPartner_api.getAsp_method()).isEqualTo(UPDATED_ASP_METHOD);
        assertThat(testPartner_api.getAsp_app_id()).isEqualTo(UPDATED_ASP_APP_ID);
        assertThat(testPartner_api.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartner_api.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPartner_api.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartner_api.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPartner_api.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPartner_api.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePartner_api() throws Exception {
        // Initialize the database
        partner_apiRepository.saveAndFlush(partner_api);

		int databaseSizeBeforeDelete = partner_apiRepository.findAll().size();

        // Get the partner_api
        restPartner_apiMockMvc.perform(delete("/api/partner_apis/{id}", partner_api.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partner_api> partner_apis = partner_apiRepository.findAll();
        assertThat(partner_apis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
