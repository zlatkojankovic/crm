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
import com.invado.crm.domain.Territory;
import com.invado.crm.repository.TerritoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TerritoryResource REST controller.
 *
 * @see TerritoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TerritoryResourceTest {


    @Inject
    private TerritoryRepository territoryRepository;

    private MockMvc restTerritoryMockMvc;

    private Territory territory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TerritoryResource territoryResource = new TerritoryResource();
        ReflectionTestUtils.setField(territoryResource, "territoryRepository", territoryRepository);
        this.restTerritoryMockMvc = MockMvcBuilders.standaloneSetup(territoryResource).build();
    }

    @Before
    public void initTest() {
        territory = new Territory();
    }

    @Test
    @Transactional
    public void createTerritory() throws Exception {
        // Validate the database is empty
        assertThat(territoryRepository.findAll()).hasSize(0);

        // Create the Territory
        restTerritoryMockMvc.perform(post("/api/territorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(territory)))
                .andExpect(status().isOk());

        // Validate the Territory in the database
        List<Territory> territorys = territoryRepository.findAll();
        assertThat(territorys).hasSize(1);
        Territory testTerritory = territorys.iterator().next();
    }

    @Test
    @Transactional
    public void getAllTerritorys() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        // Get all the territorys
        restTerritoryMockMvc.perform(get("/api/territorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(territory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getTerritory() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        // Get the territory
        restTerritoryMockMvc.perform(get("/api/territorys/{id}", territory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(territory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTerritory() throws Exception {
        // Get the territory
        restTerritoryMockMvc.perform(get("/api/territorys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerritory() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        // Update the territory
        restTerritoryMockMvc.perform(post("/api/territorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(territory)))
                .andExpect(status().isOk());

        // Validate the Territory in the database
        List<Territory> territorys = territoryRepository.findAll();
        assertThat(territorys).hasSize(1);
        Territory testTerritory = territorys.iterator().next();
    }

    @Test
    @Transactional
    public void deleteTerritory() throws Exception {
        // Initialize the database
        territoryRepository.saveAndFlush(territory);

        // Get the territory
        restTerritoryMockMvc.perform(delete("/api/territorys/{id}", territory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Territory> territorys = territoryRepository.findAll();
        assertThat(territorys).hasSize(0);
    }
}
