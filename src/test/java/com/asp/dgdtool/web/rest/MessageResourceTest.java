package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Message;
import com.asp.dgdtool.repository.MessageRepository;
import com.asp.dgdtool.repository.search.MessageSearchRepository;

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
 * Test class for the MessageResource REST controller.
 *
 * @see MessageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MessageResourceTest {


    private static final Integer DEFAULT_SENDER_ID = 1;
    private static final Integer UPDATED_SENDER_ID = 2;

    private static final Integer DEFAULT_RECIPIENT_ID = 1;
    private static final Integer UPDATED_RECIPIENT_ID = 2;

    private static final Integer DEFAULT_SENDER_DELETED = 1;
    private static final Integer UPDATED_SENDER_DELETED = 2;

    private static final Integer DEFAULT_RECIPIENT_DELETED = 1;
    private static final Integer UPDATED_RECIPIENT_DELETED = 2;

    private static final Integer DEFAULT_RECIPIENT_VIEWED = 1;
    private static final Integer UPDATED_RECIPIENT_VIEWED = 2;
    private static final String DEFAULT_MESSAGE = "AAAAA";
    private static final String UPDATED_MESSAGE = "BBBBB";

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
    private MessageRepository messageRepository;

    @Inject
    private MessageSearchRepository messageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMessageMockMvc;

    private Message message;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageResource messageResource = new MessageResource();
        ReflectionTestUtils.setField(messageResource, "messageRepository", messageRepository);
        ReflectionTestUtils.setField(messageResource, "messageSearchRepository", messageSearchRepository);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        message = new Message();
        message.setSender_id(DEFAULT_SENDER_ID);
        message.setRecipient_id(DEFAULT_RECIPIENT_ID);
        message.setSender_deleted(DEFAULT_SENDER_DELETED);
        message.setRecipient_deleted(DEFAULT_RECIPIENT_DELETED);
        message.setRecipient_viewed(DEFAULT_RECIPIENT_VIEWED);
        message.setMessage(DEFAULT_MESSAGE);
        message.setStatus_id(DEFAULT_STATUS_ID);
        message.setCreated_by(DEFAULT_CREATED_BY);
        message.setCreated_date(DEFAULT_CREATED_DATE);
        message.setModified_by(DEFAULT_MODIFIED_BY);
        message.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message

        restMessageMockMvc.perform(post("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messages.get(messages.size() - 1);
        assertThat(testMessage.getSender_id()).isEqualTo(DEFAULT_SENDER_ID);
        assertThat(testMessage.getRecipient_id()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testMessage.getSender_deleted()).isEqualTo(DEFAULT_SENDER_DELETED);
        assertThat(testMessage.getRecipient_deleted()).isEqualTo(DEFAULT_RECIPIENT_DELETED);
        assertThat(testMessage.getRecipient_viewed()).isEqualTo(DEFAULT_RECIPIENT_VIEWED);
        assertThat(testMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMessage.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testMessage.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMessage.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMessage.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMessage.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setStatus_id(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isBadRequest());

        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setCreated_by(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isBadRequest());

        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setModified_by(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isBadRequest());

        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setModified_date(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isBadRequest());

        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messages
        restMessageMockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
                .andExpect(jsonPath("$.[*].sender_id").value(hasItem(DEFAULT_SENDER_ID)))
                .andExpect(jsonPath("$.[*].recipient_id").value(hasItem(DEFAULT_RECIPIENT_ID)))
                .andExpect(jsonPath("$.[*].sender_deleted").value(hasItem(DEFAULT_SENDER_DELETED)))
                .andExpect(jsonPath("$.[*].recipient_deleted").value(hasItem(DEFAULT_RECIPIENT_DELETED)))
                .andExpect(jsonPath("$.[*].recipient_viewed").value(hasItem(DEFAULT_RECIPIENT_VIEWED)))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.sender_id").value(DEFAULT_SENDER_ID))
            .andExpect(jsonPath("$.recipient_id").value(DEFAULT_RECIPIENT_ID))
            .andExpect(jsonPath("$.sender_deleted").value(DEFAULT_SENDER_DELETED))
            .andExpect(jsonPath("$.recipient_deleted").value(DEFAULT_RECIPIENT_DELETED))
            .andExpect(jsonPath("$.recipient_viewed").value(DEFAULT_RECIPIENT_VIEWED))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

		int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        message.setSender_id(UPDATED_SENDER_ID);
        message.setRecipient_id(UPDATED_RECIPIENT_ID);
        message.setSender_deleted(UPDATED_SENDER_DELETED);
        message.setRecipient_deleted(UPDATED_RECIPIENT_DELETED);
        message.setRecipient_viewed(UPDATED_RECIPIENT_VIEWED);
        message.setMessage(UPDATED_MESSAGE);
        message.setStatus_id(UPDATED_STATUS_ID);
        message.setCreated_by(UPDATED_CREATED_BY);
        message.setCreated_date(UPDATED_CREATED_DATE);
        message.setModified_by(UPDATED_MODIFIED_BY);
        message.setModified_date(UPDATED_MODIFIED_DATE);

        restMessageMockMvc.perform(put("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messages.get(messages.size() - 1);
        assertThat(testMessage.getSender_id()).isEqualTo(UPDATED_SENDER_ID);
        assertThat(testMessage.getRecipient_id()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testMessage.getSender_deleted()).isEqualTo(UPDATED_SENDER_DELETED);
        assertThat(testMessage.getRecipient_deleted()).isEqualTo(UPDATED_RECIPIENT_DELETED);
        assertThat(testMessage.getRecipient_viewed()).isEqualTo(UPDATED_RECIPIENT_VIEWED);
        assertThat(testMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMessage.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testMessage.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMessage.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMessage.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMessage.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

		int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Get the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
