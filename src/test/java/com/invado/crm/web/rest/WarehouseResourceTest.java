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
import com.invado.crm.domain.Warehouse;
import com.invado.crm.repository.WarehouseRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WarehouseResource REST controller.
 *
 * @see WarehouseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WarehouseResourceTest {


    @Inject
    private WarehouseRepository warehouseRepository;

    private MockMvc restWarehouseMockMvc;

    private Warehouse warehouse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WarehouseResource warehouseResource = new WarehouseResource();
        ReflectionTestUtils.setField(warehouseResource, "warehouseRepository", warehouseRepository);
        this.restWarehouseMockMvc = MockMvcBuilders.standaloneSetup(warehouseResource).build();
    }

    @Before
    public void initTest() {
        warehouse = new Warehouse();
    }

    @Test
    @Transactional
    public void createWarehouse() throws Exception {
        // Validate the database is empty
        assertThat(warehouseRepository.findAll()).hasSize(0);

        // Create the Warehouse
        restWarehouseMockMvc.perform(post("/api/warehouses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(warehouse)))
                .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(1);
        Warehouse testWarehouse = warehouses.iterator().next();
    }

    @Test
    @Transactional
    public void getAllWarehouses() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouses
        restWarehouseMockMvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(warehouse.getId().intValue()));
    }

    @Test
    @Transactional
    public void getWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get the warehouse
        restWarehouseMockMvc.perform(get("/api/warehouses/{id}", warehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(warehouse.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWarehouse() throws Exception {
        // Get the warehouse
        restWarehouseMockMvc.perform(get("/api/warehouses/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Update the warehouse
        restWarehouseMockMvc.perform(post("/api/warehouses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(warehouse)))
                .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(1);
        Warehouse testWarehouse = warehouses.iterator().next();
    }

    @Test
    @Transactional
    public void deleteWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get the warehouse
        restWarehouseMockMvc.perform(delete("/api/warehouses/{id}", warehouse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Warehouse> warehouses = warehouseRepository.findAll();
        assertThat(warehouses).hasSize(0);
    }
}
