package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Menu;
import com.asp.dgdtool.repository.MenuRepository;
import com.asp.dgdtool.repository.search.MenuSearchRepository;

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
 * Test class for the MenuResource REST controller.
 *
 * @see MenuResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MenuResourceTest {


    private static final Integer DEFAULT_PARENT_MENU_ID = 1;
    private static final Integer UPDATED_PARENT_MENU_ID = 2;
    private static final String DEFAULT_MENU_ITEM_NAME = "AAAAA";
    private static final String UPDATED_MENU_ITEM_NAME = "BBBBB";
    private static final String DEFAULT_CONTROLLER_URL = "AAAAA";
    private static final String UPDATED_CONTROLLER_URL = "BBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;
    private static final String DEFAULT_MENU_HINT = "AAAAA";
    private static final String UPDATED_MENU_HINT = "BBBBB";
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
    private MenuRepository menuRepository;

    @Inject
    private MenuSearchRepository menuSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMenuMockMvc;

    private Menu menu;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MenuResource menuResource = new MenuResource();
        ReflectionTestUtils.setField(menuResource, "menuRepository", menuRepository);
        ReflectionTestUtils.setField(menuResource, "menuSearchRepository", menuSearchRepository);
        this.restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        menu = new Menu();
        menu.setParent_menu_id(DEFAULT_PARENT_MENU_ID);
        menu.setMenu_item_name(DEFAULT_MENU_ITEM_NAME);
        menu.setController_url(DEFAULT_CONTROLLER_URL);
        menu.setDisplay_order(DEFAULT_DISPLAY_ORDER);
        menu.setMenu_hint(DEFAULT_MENU_HINT);
        menu.setDescription(DEFAULT_DESCRIPTION);
        menu.setStatus_id(DEFAULT_STATUS_ID);
        menu.setCreated_by(DEFAULT_CREATED_BY);
        menu.setCreated_date(DEFAULT_CREATED_DATE);
        menu.setModified_by(DEFAULT_MODIFIED_BY);
        menu.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menus.get(menus.size() - 1);
        assertThat(testMenu.getParent_menu_id()).isEqualTo(DEFAULT_PARENT_MENU_ID);
        assertThat(testMenu.getMenu_item_name()).isEqualTo(DEFAULT_MENU_ITEM_NAME);
        assertThat(testMenu.getController_url()).isEqualTo(DEFAULT_CONTROLLER_URL);
        assertThat(testMenu.getDisplay_order()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testMenu.getMenu_hint()).isEqualTo(DEFAULT_MENU_HINT);
        assertThat(testMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMenu.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testMenu.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMenu.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMenu.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMenu.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkParent_menu_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setParent_menu_id(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMenu_item_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setMenu_item_name(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkController_urlIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setController_url(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplay_orderIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setDisplay_order(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMenu_hintIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setMenu_hint(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setDescription(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setStatus_id(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setCreated_by(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setCreated_date(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setModified_by(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setModified_date(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isBadRequest());

        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menus
        restMenuMockMvc.perform(get("/api/menus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
                .andExpect(jsonPath("$.[*].parent_menu_id").value(hasItem(DEFAULT_PARENT_MENU_ID)))
                .andExpect(jsonPath("$.[*].menu_item_name").value(hasItem(DEFAULT_MENU_ITEM_NAME.toString())))
                .andExpect(jsonPath("$.[*].controller_url").value(hasItem(DEFAULT_CONTROLLER_URL.toString())))
                .andExpect(jsonPath("$.[*].display_order").value(hasItem(DEFAULT_DISPLAY_ORDER)))
                .andExpect(jsonPath("$.[*].menu_hint").value(hasItem(DEFAULT_MENU_HINT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(menu.getId().intValue()))
            .andExpect(jsonPath("$.parent_menu_id").value(DEFAULT_PARENT_MENU_ID))
            .andExpect(jsonPath("$.menu_item_name").value(DEFAULT_MENU_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.controller_url").value(DEFAULT_CONTROLLER_URL.toString()))
            .andExpect(jsonPath("$.display_order").value(DEFAULT_DISPLAY_ORDER))
            .andExpect(jsonPath("$.menu_hint").value(DEFAULT_MENU_HINT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

		int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        menu.setParent_menu_id(UPDATED_PARENT_MENU_ID);
        menu.setMenu_item_name(UPDATED_MENU_ITEM_NAME);
        menu.setController_url(UPDATED_CONTROLLER_URL);
        menu.setDisplay_order(UPDATED_DISPLAY_ORDER);
        menu.setMenu_hint(UPDATED_MENU_HINT);
        menu.setDescription(UPDATED_DESCRIPTION);
        menu.setStatus_id(UPDATED_STATUS_ID);
        menu.setCreated_by(UPDATED_CREATED_BY);
        menu.setCreated_date(UPDATED_CREATED_DATE);
        menu.setModified_by(UPDATED_MODIFIED_BY);
        menu.setModified_date(UPDATED_MODIFIED_DATE);

        restMenuMockMvc.perform(put("/api/menus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu)))
                .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menus.get(menus.size() - 1);
        assertThat(testMenu.getParent_menu_id()).isEqualTo(UPDATED_PARENT_MENU_ID);
        assertThat(testMenu.getMenu_item_name()).isEqualTo(UPDATED_MENU_ITEM_NAME);
        assertThat(testMenu.getController_url()).isEqualTo(UPDATED_CONTROLLER_URL);
        assertThat(testMenu.getDisplay_order()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testMenu.getMenu_hint()).isEqualTo(UPDATED_MENU_HINT);
        assertThat(testMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenu.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testMenu.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMenu.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMenu.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMenu.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

		int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Get the menu
        restMenuMockMvc.perform(delete("/api/menus/{id}", menu.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
