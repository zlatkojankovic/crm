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
import com.invado.crm.domain.BusinessPartnerAddress;
import com.invado.crm.repository.BusinessPartnerAddressRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessPartnerAddressResource REST controller.
 *
 * @see BusinessPartnerAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusinessPartnerAddressResourceTest {

    private static final String DEFAULT_STREET = "SAMPLE_TEXT";
    private static final String UPDATED_STREET = "UPDATED_TEXT";

    private static final Integer DEFAULT_NUMBER = 0;
    private static final Integer UPDATED_NUMBER = 1;

    @Inject
    private BusinessPartnerAddressRepository businessPartnerAddressRepository;

    private MockMvc restBusinessPartnerAddressMockMvc;

    private BusinessPartnerAddress businessPartnerAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessPartnerAddressResource businessPartnerAddressResource = new BusinessPartnerAddressResource();
        ReflectionTestUtils.setField(businessPartnerAddressResource, "businessPartnerAddressRepository", businessPartnerAddressRepository);
        this.restBusinessPartnerAddressMockMvc = MockMvcBuilders.standaloneSetup(businessPartnerAddressResource).build();
    }

    @Before
    public void initTest() {
        businessPartnerAddress = new BusinessPartnerAddress();
        businessPartnerAddress.setStreet(DEFAULT_STREET);
        businessPartnerAddress.setNumber(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createBusinessPartnerAddress() throws Exception {
        // Validate the database is empty
        assertThat(businessPartnerAddressRepository.findAll()).hasSize(0);

        // Create the BusinessPartnerAddress
        restBusinessPartnerAddressMockMvc.perform(post("/api/businessPartnerAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerAddress)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerAddress in the database
        List<BusinessPartnerAddress> businessPartnerAddresss = businessPartnerAddressRepository.findAll();
        assertThat(businessPartnerAddresss).hasSize(1);
        BusinessPartnerAddress testBusinessPartnerAddress = businessPartnerAddresss.iterator().next();
        assertThat(testBusinessPartnerAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testBusinessPartnerAddress.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBusinessPartnerAddresss() throws Exception {
        // Initialize the database
        businessPartnerAddressRepository.saveAndFlush(businessPartnerAddress);

        // Get all the businessPartnerAddresss
        restBusinessPartnerAddressMockMvc.perform(get("/api/businessPartnerAddresss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(businessPartnerAddress.getId().intValue()))
                .andExpect(jsonPath("$.[0].street").value(DEFAULT_STREET.toString()))
                .andExpect(jsonPath("$.[0].number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getBusinessPartnerAddress() throws Exception {
        // Initialize the database
        businessPartnerAddressRepository.saveAndFlush(businessPartnerAddress);

        // Get the businessPartnerAddress
        restBusinessPartnerAddressMockMvc.perform(get("/api/businessPartnerAddresss/{id}", businessPartnerAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(businessPartnerAddress.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessPartnerAddress() throws Exception {
        // Get the businessPartnerAddress
        restBusinessPartnerAddressMockMvc.perform(get("/api/businessPartnerAddresss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessPartnerAddress() throws Exception {
        // Initialize the database
        businessPartnerAddressRepository.saveAndFlush(businessPartnerAddress);

        // Update the businessPartnerAddress
        businessPartnerAddress.setStreet(UPDATED_STREET);
        businessPartnerAddress.setNumber(UPDATED_NUMBER);
        restBusinessPartnerAddressMockMvc.perform(post("/api/businessPartnerAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerAddress)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerAddress in the database
        List<BusinessPartnerAddress> businessPartnerAddresss = businessPartnerAddressRepository.findAll();
        assertThat(businessPartnerAddresss).hasSize(1);
        BusinessPartnerAddress testBusinessPartnerAddress = businessPartnerAddresss.iterator().next();
        assertThat(testBusinessPartnerAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testBusinessPartnerAddress.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void deleteBusinessPartnerAddress() throws Exception {
        // Initialize the database
        businessPartnerAddressRepository.saveAndFlush(businessPartnerAddress);

        // Get the businessPartnerAddress
        restBusinessPartnerAddressMockMvc.perform(delete("/api/businessPartnerAddresss/{id}", businessPartnerAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessPartnerAddress> businessPartnerAddresss = businessPartnerAddressRepository.findAll();
        assertThat(businessPartnerAddresss).hasSize(0);
    }
}
