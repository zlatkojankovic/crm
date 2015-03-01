package com.invado.crm.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.invado.crm.Application;
import com.invado.crm.domain.TerritoryType;
import com.invado.crm.repository.TerritoryTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TerritoryTypeResource REST controller.
 *
 * @see TerritoryTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TerritoryTypeResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ENTRY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ENTRY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ENTRY_STR = dateTimeFormatter.print(DEFAULT_DATE_ENTRY);

    @Inject
    private TerritoryTypeRepository territoryTypeRepository;

    private MockMvc restTerritoryTypeMockMvc;

    private TerritoryType territoryType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TerritoryTypeResource territoryTypeResource = new TerritoryTypeResource();
        ReflectionTestUtils.setField(territoryTypeResource, "territoryTypeRepository", territoryTypeRepository);
        this.restTerritoryTypeMockMvc = MockMvcBuilders.standaloneSetup(territoryTypeResource).build();
    }

    @Before
    public void initTest() {
        territoryType = new TerritoryType();
        territoryType.setName(DEFAULT_NAME);
        territoryType.setDescription(DEFAULT_DESCRIPTION);
        territoryType.setDateEntry(DEFAULT_DATE_ENTRY);
    }

    @Test
    @Transactional
    public void createTerritoryType() throws Exception {
        // Validate the database is empty
        assertThat(territoryTypeRepository.findAll()).hasSize(0);

        // Create the TerritoryType
        restTerritoryTypeMockMvc.perform(post("/api/territoryTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(territoryType)))
                .andExpect(status().isOk());

        // Validate the TerritoryType in the database
        List<TerritoryType> territoryTypes = territoryTypeRepository.findAll();
        assertThat(territoryTypes).hasSize(1);
        TerritoryType testTerritoryType = territoryTypes.iterator().next();
        assertThat(testTerritoryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTerritoryType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTerritoryType.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ENTRY);
    }

    @Test
    @Transactional
    public void getAllTerritoryTypes() throws Exception {
        // Initialize the database
        territoryTypeRepository.saveAndFlush(territoryType);

        // Get all the territoryTypes
        restTerritoryTypeMockMvc.perform(get("/api/territoryTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(territoryType.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].dateEntry").value(DEFAULT_DATE_ENTRY_STR));
    }

    @Test
    @Transactional
    public void getTerritoryType() throws Exception {
        // Initialize the database
        territoryTypeRepository.saveAndFlush(territoryType);

        // Get the territoryType
        restTerritoryTypeMockMvc.perform(get("/api/territoryTypes/{id}", territoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(territoryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateEntry").value(DEFAULT_DATE_ENTRY_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTerritoryType() throws Exception {
        // Get the territoryType
        restTerritoryTypeMockMvc.perform(get("/api/territoryTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerritoryType() throws Exception {
        // Initialize the database
        territoryTypeRepository.saveAndFlush(territoryType);

        // Update the territoryType
        territoryType.setName(UPDATED_NAME);
        territoryType.setDescription(UPDATED_DESCRIPTION);
        territoryType.setDateEntry(UPDATED_DATE_ENTRY);
        restTerritoryTypeMockMvc.perform(post("/api/territoryTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(territoryType)))
                .andExpect(status().isOk());

        // Validate the TerritoryType in the database
        List<TerritoryType> territoryTypes = territoryTypeRepository.findAll();
        assertThat(territoryTypes).hasSize(1);
        TerritoryType testTerritoryType = territoryTypes.iterator().next();
        assertThat(testTerritoryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerritoryType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTerritoryType.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ENTRY);
    }

    @Test
    @Transactional
    public void deleteTerritoryType() throws Exception {
        // Initialize the database
        territoryTypeRepository.saveAndFlush(territoryType);

        // Get the territoryType
        restTerritoryTypeMockMvc.perform(delete("/api/territoryTypes/{id}", territoryType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TerritoryType> territoryTypes = territoryTypeRepository.findAll();
        assertThat(territoryTypes).hasSize(0);
    }
}
