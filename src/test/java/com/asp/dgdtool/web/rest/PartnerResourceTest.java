package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Partner;
import com.asp.dgdtool.repository.PartnerRepository;
import com.asp.dgdtool.repository.search.PartnerSearchRepository;

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
 * Test class for the PartnerResource REST controller.
 *
 * @see PartnerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartnerResourceTest {

    private static final String DEFAULT_PARTNER_NAME = "AAAAA";
    private static final String UPDATED_PARTNER_NAME = "BBBBB";
    private static final String DEFAULT_PARTNER_KEY = "AAAAA";
    private static final String UPDATED_PARTNER_KEY = "BBBBB";
    private static final String DEFAULT_PARTNER_BASE_URL = "AAAAA";
    private static final String UPDATED_PARTNER_BASE_URL = "BBBBB";
    private static final String DEFAULT_PARTNER_BROKER_URL = "AAAAA";
    private static final String UPDATED_PARTNER_BROKER_URL = "BBBBB";
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
    private PartnerRepository partnerRepository;

    @Inject
    private PartnerSearchRepository partnerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartnerResource partnerResource = new PartnerResource();
        ReflectionTestUtils.setField(partnerResource, "partnerRepository", partnerRepository);
        ReflectionTestUtils.setField(partnerResource, "partnerSearchRepository", partnerSearchRepository);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        partner = new Partner();
        partner.setPartner_name(DEFAULT_PARTNER_NAME);
        partner.setPartner_key(DEFAULT_PARTNER_KEY);
        partner.setPartner_base_url(DEFAULT_PARTNER_BASE_URL);
        partner.setPartner_broker_url(DEFAULT_PARTNER_BROKER_URL);
        partner.setDescription(DEFAULT_DESCRIPTION);
        partner.setStatus_id(DEFAULT_STATUS_ID);
        partner.setCreated_by(DEFAULT_CREATED_BY);
        partner.setCreated_date(DEFAULT_CREATED_DATE);
        partner.setModified_by(DEFAULT_MODIFIED_BY);
        partner.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partners.get(partners.size() - 1);
        assertThat(testPartner.getPartner_name()).isEqualTo(DEFAULT_PARTNER_NAME);
        assertThat(testPartner.getPartner_key()).isEqualTo(DEFAULT_PARTNER_KEY);
        assertThat(testPartner.getPartner_base_url()).isEqualTo(DEFAULT_PARTNER_BASE_URL);
        assertThat(testPartner.getPartner_broker_url()).isEqualTo(DEFAULT_PARTNER_BROKER_URL);
        assertThat(testPartner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartner.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPartner.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPartner.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPartner.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPartner.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPartner_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPartner_name(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner_keyIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPartner_key(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner_base_urlIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPartner_base_url(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner_broker_urlIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPartner_broker_url(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setDescription(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setStatus_id(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCreated_by(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCreated_date(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setModified_by(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setModified_date(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partners
        restPartnerMockMvc.perform(get("/api/partners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
                .andExpect(jsonPath("$.[*].partner_name").value(hasItem(DEFAULT_PARTNER_NAME.toString())))
                .andExpect(jsonPath("$.[*].partner_key").value(hasItem(DEFAULT_PARTNER_KEY.toString())))
                .andExpect(jsonPath("$.[*].partner_base_url").value(hasItem(DEFAULT_PARTNER_BASE_URL.toString())))
                .andExpect(jsonPath("$.[*].partner_broker_url").value(hasItem(DEFAULT_PARTNER_BROKER_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partner.getId().intValue()))
            .andExpect(jsonPath("$.partner_name").value(DEFAULT_PARTNER_NAME.toString()))
            .andExpect(jsonPath("$.partner_key").value(DEFAULT_PARTNER_KEY.toString()))
            .andExpect(jsonPath("$.partner_base_url").value(DEFAULT_PARTNER_BASE_URL.toString()))
            .andExpect(jsonPath("$.partner_broker_url").value(DEFAULT_PARTNER_BROKER_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

		int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        partner.setPartner_name(UPDATED_PARTNER_NAME);
        partner.setPartner_key(UPDATED_PARTNER_KEY);
        partner.setPartner_base_url(UPDATED_PARTNER_BASE_URL);
        partner.setPartner_broker_url(UPDATED_PARTNER_BROKER_URL);
        partner.setDescription(UPDATED_DESCRIPTION);
        partner.setStatus_id(UPDATED_STATUS_ID);
        partner.setCreated_by(UPDATED_CREATED_BY);
        partner.setCreated_date(UPDATED_CREATED_DATE);
        partner.setModified_by(UPDATED_MODIFIED_BY);
        partner.setModified_date(UPDATED_MODIFIED_DATE);

        restPartnerMockMvc.perform(put("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partners.get(partners.size() - 1);
        assertThat(testPartner.getPartner_name()).isEqualTo(UPDATED_PARTNER_NAME);
        assertThat(testPartner.getPartner_key()).isEqualTo(UPDATED_PARTNER_KEY);
        assertThat(testPartner.getPartner_base_url()).isEqualTo(UPDATED_PARTNER_BASE_URL);
        assertThat(testPartner.getPartner_broker_url()).isEqualTo(UPDATED_PARTNER_BROKER_URL);
        assertThat(testPartner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartner.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPartner.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPartner.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPartner.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPartner.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

		int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Get the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeDelete - 1);
    }
}
