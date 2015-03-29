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
import java.util.List;

import com.invado.crm.Application;
import com.invado.crm.domain.UnitOfMeasure;
import com.invado.crm.repository.UnitOfMeasureRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UnitOfMeasureResource REST controller.
 *
 * @see UnitOfMeasureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UnitOfMeasureResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SHORT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_SHORT_NAME = "UPDATED_TEXT";

    @Inject
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private MockMvc restUnitOfMeasureMockMvc;

    private UnitOfMeasure unitOfMeasure;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UnitOfMeasureResource unitOfMeasureResource = new UnitOfMeasureResource();
        ReflectionTestUtils.setField(unitOfMeasureResource, "unitOfMeasureRepository", unitOfMeasureRepository);
        this.restUnitOfMeasureMockMvc = MockMvcBuilders.standaloneSetup(unitOfMeasureResource).build();
    }

    @Before
    public void initTest() {
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setName(DEFAULT_NAME);
        unitOfMeasure.setShortName(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void createUnitOfMeasure() throws Exception {
        // Validate the database is empty
        assertThat(unitOfMeasureRepository.findAll()).hasSize(0);

        // Create the UnitOfMeasure
        restUnitOfMeasureMockMvc.perform(post("/api/unitOfMeasures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
                .andExpect(status().isOk());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasures).hasSize(1);
        UnitOfMeasure testUnitOfMeasure = unitOfMeasures.iterator().next();
        assertThat(testUnitOfMeasure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnitOfMeasure.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitOfMeasures() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get all the unitOfMeasures
        restUnitOfMeasureMockMvc.perform(get("/api/unitOfMeasures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(unitOfMeasure.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(get("/api/unitOfMeasures/{id}", unitOfMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(unitOfMeasure.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnitOfMeasure() throws Exception {
        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(get("/api/unitOfMeasures/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Update the unitOfMeasure
        unitOfMeasure.setName(UPDATED_NAME);
        unitOfMeasure.setShortName(UPDATED_SHORT_NAME);
        restUnitOfMeasureMockMvc.perform(post("/api/unitOfMeasures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
                .andExpect(status().isOk());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasures).hasSize(1);
        UnitOfMeasure testUnitOfMeasure = unitOfMeasures.iterator().next();
        assertThat(testUnitOfMeasure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnitOfMeasure.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void deleteUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(delete("/api/unitOfMeasures/{id}", unitOfMeasure.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasures).hasSize(0);
    }
}
