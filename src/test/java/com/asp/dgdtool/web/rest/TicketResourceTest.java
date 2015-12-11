package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Ticket;
import com.asp.dgdtool.repository.TicketRepository;
import com.asp.dgdtool.repository.search.TicketSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TicketResource REST controller.
 *
 * @see TicketResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TicketResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TICKET_REF_NUMBER = "AAAAA";
    private static final String UPDATED_TICKET_REF_NUMBER = "BBBBB";

    private static final ZonedDateTime DEFAULT_TICKET_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TICKET_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TICKET_CREATED_DATE_STR = dateTimeFormatter.format(DEFAULT_TICKET_CREATED_DATE);
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
    private TicketRepository ticketRepository;

    @Inject
    private TicketSearchRepository ticketSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTicketMockMvc;

    private Ticket ticket;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TicketResource ticketResource = new TicketResource();
        ReflectionTestUtils.setField(ticketResource, "ticketRepository", ticketRepository);
        ReflectionTestUtils.setField(ticketResource, "ticketSearchRepository", ticketSearchRepository);
        this.restTicketMockMvc = MockMvcBuilders.standaloneSetup(ticketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ticket = new Ticket();
        ticket.setTicket_ref_number(DEFAULT_TICKET_REF_NUMBER);
        ticket.setTicket_created_date(DEFAULT_TICKET_CREATED_DATE);
        ticket.setDescription(DEFAULT_DESCRIPTION);
        ticket.setStatus_id(DEFAULT_STATUS_ID);
        ticket.setCreated_by(DEFAULT_CREATED_BY);
        ticket.setCreated_date(DEFAULT_CREATED_DATE);
        ticket.setModified_by(DEFAULT_MODIFIED_BY);
        ticket.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createTicket() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // Create the Ticket

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeCreate + 1);
        Ticket testTicket = tickets.get(tickets.size() - 1);
        assertThat(testTicket.getTicket_ref_number()).isEqualTo(DEFAULT_TICKET_REF_NUMBER);
        assertThat(testTicket.getTicket_created_date()).isEqualTo(DEFAULT_TICKET_CREATED_DATE);
        assertThat(testTicket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTicket.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testTicket.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTicket.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTicket.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTicket.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkTicket_ref_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setTicket_ref_number(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setDescription(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setStatus_id(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setCreated_by(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setCreated_date(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setModified_by(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setModified_date(null);

        // Create the Ticket, which fails.

        restTicketMockMvc.perform(post("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isBadRequest());

        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTickets() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get all the tickets
        restTicketMockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
                .andExpect(jsonPath("$.[*].ticket_ref_number").value(hasItem(DEFAULT_TICKET_REF_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].ticket_created_date").value(hasItem(DEFAULT_TICKET_CREATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", ticket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ticket.getId().intValue()))
            .andExpect(jsonPath("$.ticket_ref_number").value(DEFAULT_TICKET_REF_NUMBER.toString()))
            .andExpect(jsonPath("$.ticket_created_date").value(DEFAULT_TICKET_CREATED_DATE_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTicket() throws Exception {
        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

		int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket
        ticket.setTicket_ref_number(UPDATED_TICKET_REF_NUMBER);
        ticket.setTicket_created_date(UPDATED_TICKET_CREATED_DATE);
        ticket.setDescription(UPDATED_DESCRIPTION);
        ticket.setStatus_id(UPDATED_STATUS_ID);
        ticket.setCreated_by(UPDATED_CREATED_BY);
        ticket.setCreated_date(UPDATED_CREATED_DATE);
        ticket.setModified_by(UPDATED_MODIFIED_BY);
        ticket.setModified_date(UPDATED_MODIFIED_DATE);

        restTicketMockMvc.perform(put("/api/tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticket)))
                .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = tickets.get(tickets.size() - 1);
        assertThat(testTicket.getTicket_ref_number()).isEqualTo(UPDATED_TICKET_REF_NUMBER);
        assertThat(testTicket.getTicket_created_date()).isEqualTo(UPDATED_TICKET_CREATED_DATE);
        assertThat(testTicket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTicket.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testTicket.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTicket.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTicket.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTicket.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

		int databaseSizeBeforeDelete = ticketRepository.findAll().size();

        // Get the ticket
        restTicketMockMvc.perform(delete("/api/tickets/{id}", ticket.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ticket> tickets = ticketRepository.findAll();
        assertThat(tickets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
