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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.invado.crm.Application;
import com.invado.crm.domain.BusinessPartner;
import com.invado.crm.repository.BusinessPartnerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessPartnerResource REST controller.
 *
 * @see BusinessPartnerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusinessPartnerResourceTest {

    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PIB = "SAMPLE_TEXT";
    private static final String UPDATED_PIB = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_REGISTRATION_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_REGISTRATION_NUMBER = "UPDATED_TEXT";

    @Inject
    private BusinessPartnerRepository businessPartnerRepository;

    private MockMvc restBusinessPartnerMockMvc;

    private BusinessPartner businessPartner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessPartnerResource businessPartnerResource = new BusinessPartnerResource();
        ReflectionTestUtils.setField(businessPartnerResource, "businessPartnerRepository", businessPartnerRepository);
        this.restBusinessPartnerMockMvc = MockMvcBuilders.standaloneSetup(businessPartnerResource).build();
    }

    @Before
    public void initTest() {
        businessPartner = new BusinessPartner();
        businessPartner.setEmail(DEFAULT_EMAIL);
        businessPartner.setPIB(DEFAULT_PIB);
        businessPartner.setStatus(DEFAULT_STATUS);
        businessPartner.setRegistrationNumber(DEFAULT_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void createBusinessPartner() throws Exception {
        // Validate the database is empty
        assertThat(businessPartnerRepository.findAll()).hasSize(0);

        // Create the BusinessPartner
        restBusinessPartnerMockMvc.perform(post("/api/businessPartners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartner)))
                .andExpect(status().isOk());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartners = businessPartnerRepository.findAll();
        assertThat(businessPartners).hasSize(1);
        BusinessPartner testBusinessPartner = businessPartners.iterator().next();
        assertThat(testBusinessPartner.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBusinessPartner.getPIB()).isEqualTo(DEFAULT_PIB);
        assertThat(testBusinessPartner.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBusinessPartner.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBusinessPartners() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        // Get all the businessPartners
        restBusinessPartnerMockMvc.perform(get("/api/businessPartners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(businessPartner.getId().intValue()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].PIB").value(DEFAULT_PIB.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].registrationNumber").value(DEFAULT_REGISTRATION_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getBusinessPartner() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        // Get the businessPartner
        restBusinessPartnerMockMvc.perform(get("/api/businessPartners/{id}", businessPartner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(businessPartner.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.PIB").value(DEFAULT_PIB.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessPartner() throws Exception {
        // Get the businessPartner
        restBusinessPartnerMockMvc.perform(get("/api/businessPartners/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessPartner() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        // Update the businessPartner
        businessPartner.setEmail(UPDATED_EMAIL);
        businessPartner.setPIB(UPDATED_PIB);
        businessPartner.setStatus(UPDATED_STATUS);
        businessPartner.setRegistrationNumber(UPDATED_REGISTRATION_NUMBER);
        restBusinessPartnerMockMvc.perform(post("/api/businessPartners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartner)))
                .andExpect(status().isOk());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartners = businessPartnerRepository.findAll();
        assertThat(businessPartners).hasSize(1);
        BusinessPartner testBusinessPartner = businessPartners.iterator().next();
        assertThat(testBusinessPartner.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBusinessPartner.getPIB()).isEqualTo(UPDATED_PIB);
        assertThat(testBusinessPartner.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBusinessPartner.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    public void deleteBusinessPartner() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        // Get the businessPartner
        restBusinessPartnerMockMvc.perform(delete("/api/businessPartners/{id}", businessPartner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessPartner> businessPartners = businessPartnerRepository.findAll();
        assertThat(businessPartners).hasSize(0);
    }


}
