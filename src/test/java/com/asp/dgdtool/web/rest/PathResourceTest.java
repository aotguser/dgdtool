package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Path;
import com.asp.dgdtool.repository.PathRepository;
import com.asp.dgdtool.repository.search.PathSearchRepository;

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
 * Test class for the PathResource REST controller.
 *
 * @see PathResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PathResourceTest {

    private static final String DEFAULT_PATH_NAME = "AAAAA";
    private static final String UPDATED_PATH_NAME = "BBBBB";
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
    private PathRepository pathRepository;

    @Inject
    private PathSearchRepository pathSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPathMockMvc;

    private Path path;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PathResource pathResource = new PathResource();
        ReflectionTestUtils.setField(pathResource, "pathRepository", pathRepository);
        ReflectionTestUtils.setField(pathResource, "pathSearchRepository", pathSearchRepository);
        this.restPathMockMvc = MockMvcBuilders.standaloneSetup(pathResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        path = new Path();
        path.setPath_name(DEFAULT_PATH_NAME);
        path.setDescription(DEFAULT_DESCRIPTION);
        path.setStatus_id(DEFAULT_STATUS_ID);
        path.setCreated_by(DEFAULT_CREATED_BY);
        path.setCreated_date(DEFAULT_CREATED_DATE);
        path.setModified_by(DEFAULT_MODIFIED_BY);
        path.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPath() throws Exception {
        int databaseSizeBeforeCreate = pathRepository.findAll().size();

        // Create the Path

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isCreated());

        // Validate the Path in the database
        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeCreate + 1);
        Path testPath = paths.get(paths.size() - 1);
        assertThat(testPath.getPath_name()).isEqualTo(DEFAULT_PATH_NAME);
        assertThat(testPath.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPath.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPath.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPath.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPath.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPath.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPath_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setPath_name(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setDescription(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setStatus_id(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setCreated_by(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setCreated_date(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setModified_by(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pathRepository.findAll().size();
        // set the field null
        path.setModified_date(null);

        // Create the Path, which fails.

        restPathMockMvc.perform(post("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isBadRequest());

        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaths() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get all the paths
        restPathMockMvc.perform(get("/api/paths"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(path.getId().intValue())))
                .andExpect(jsonPath("$.[*].path_name").value(hasItem(DEFAULT_PATH_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get the path
        restPathMockMvc.perform(get("/api/paths/{id}", path.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(path.getId().intValue()))
            .andExpect(jsonPath("$.path_name").value(DEFAULT_PATH_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPath() throws Exception {
        // Get the path
        restPathMockMvc.perform(get("/api/paths/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

		int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path
        path.setPath_name(UPDATED_PATH_NAME);
        path.setDescription(UPDATED_DESCRIPTION);
        path.setStatus_id(UPDATED_STATUS_ID);
        path.setCreated_by(UPDATED_CREATED_BY);
        path.setCreated_date(UPDATED_CREATED_DATE);
        path.setModified_by(UPDATED_MODIFIED_BY);
        path.setModified_date(UPDATED_MODIFIED_DATE);

        restPathMockMvc.perform(put("/api/paths")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(path)))
                .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeUpdate);
        Path testPath = paths.get(paths.size() - 1);
        assertThat(testPath.getPath_name()).isEqualTo(UPDATED_PATH_NAME);
        assertThat(testPath.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPath.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPath.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPath.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPath.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPath.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

		int databaseSizeBeforeDelete = pathRepository.findAll().size();

        // Get the path
        restPathMockMvc.perform(delete("/api/paths/{id}", path.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Path> paths = pathRepository.findAll();
        assertThat(paths).hasSize(databaseSizeBeforeDelete - 1);
    }
}
