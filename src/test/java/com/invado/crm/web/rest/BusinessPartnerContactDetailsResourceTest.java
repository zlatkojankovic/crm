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
import com.invado.crm.domain.BusinessPartnerContactDetails;
import com.invado.crm.repository.BusinessPartnerContactDetailsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessPartnerContactDetailsResource REST controller.
 *
 * @see BusinessPartnerContactDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusinessPartnerContactDetailsResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LAST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_FROM = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_FROM = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_FROM_STR = dateTimeFormatter.print(DEFAULT_DATE_FROM);

    private static final DateTime DEFAULT_DATE_TO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_TO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_TO_STR = dateTimeFormatter.print(DEFAULT_DATE_TO);

    @Inject
    private BusinessPartnerContactDetailsRepository businessPartnerContactDetailsRepository;

    private MockMvc restBusinessPartnerContactDetailsMockMvc;

    private BusinessPartnerContactDetails businessPartnerContactDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessPartnerContactDetailsResource businessPartnerContactDetailsResource = new BusinessPartnerContactDetailsResource();
        ReflectionTestUtils.setField(businessPartnerContactDetailsResource, "businessPartnerContactDetailsRepository", businessPartnerContactDetailsRepository);
        this.restBusinessPartnerContactDetailsMockMvc = MockMvcBuilders.standaloneSetup(businessPartnerContactDetailsResource).build();
    }

    @Before
    public void initTest() {
        businessPartnerContactDetails = new BusinessPartnerContactDetails();
        businessPartnerContactDetails.setFirstName(DEFAULT_FIRST_NAME);
        businessPartnerContactDetails.setLastName(DEFAULT_LAST_NAME);
        businessPartnerContactDetails.setEmail(DEFAULT_EMAIL);
        businessPartnerContactDetails.setAddress(DEFAULT_ADDRESS);
        businessPartnerContactDetails.setDateFrom(DEFAULT_DATE_FROM);
        businessPartnerContactDetails.setDateTo(DEFAULT_DATE_TO);
    }

    @Test
    @Transactional
    public void createBusinessPartnerContactDetails() throws Exception {
        // Validate the database is empty
        assertThat(businessPartnerContactDetailsRepository.findAll()).hasSize(0);

        // Create the BusinessPartnerContactDetails
        restBusinessPartnerContactDetailsMockMvc.perform(post("/api/businessPartnerContactDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerContactDetails)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerContactDetails in the database
        List<BusinessPartnerContactDetails> businessPartnerContactDetailss = businessPartnerContactDetailsRepository.findAll();
        assertThat(businessPartnerContactDetailss).hasSize(1);
        BusinessPartnerContactDetails testBusinessPartnerContactDetails = businessPartnerContactDetailss.iterator().next();
        assertThat(testBusinessPartnerContactDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBusinessPartnerContactDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBusinessPartnerContactDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBusinessPartnerContactDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBusinessPartnerContactDetails.getDateFrom().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testBusinessPartnerContactDetails.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllBusinessPartnerContactDetailss() throws Exception {
        // Initialize the database
        businessPartnerContactDetailsRepository.saveAndFlush(businessPartnerContactDetails);

        // Get all the businessPartnerContactDetailss
        restBusinessPartnerContactDetailsMockMvc.perform(get("/api/businessPartnerContactDetailss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(businessPartnerContactDetails.getId().intValue()))
                .andExpect(jsonPath("$.[0].firstName").value(DEFAULT_FIRST_NAME.toString()))
                .andExpect(jsonPath("$.[0].lastName").value(DEFAULT_LAST_NAME.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].address").value(DEFAULT_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].dateFrom").value(DEFAULT_DATE_FROM_STR))
                .andExpect(jsonPath("$.[0].dateTo").value(DEFAULT_DATE_TO_STR));
    }

    @Test
    @Transactional
    public void getBusinessPartnerContactDetails() throws Exception {
        // Initialize the database
        businessPartnerContactDetailsRepository.saveAndFlush(businessPartnerContactDetails);

        // Get the businessPartnerContactDetails
        restBusinessPartnerContactDetailsMockMvc.perform(get("/api/businessPartnerContactDetailss/{id}", businessPartnerContactDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(businessPartnerContactDetails.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM_STR))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO_STR));
    }


    @Test
    @Transactional
    public void getBusinessPartnerContactDetailsPerPartner() throws Exception {
        // Initialize the database
        businessPartnerContactDetailsRepository.saveAndFlush(businessPartnerContactDetails);

        // Get the businessPartnerContactDetails
        restBusinessPartnerContactDetailsMockMvc.perform(get("/api/businessPartnersContactDetailssPerPartner/{partnerId}", 7))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].id").value(businessPartnerContactDetails.getId().intValue()))
            .andExpect(jsonPath("$.[0].firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.[0].lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.[0].address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.[0].dateFrom").value(DEFAULT_DATE_FROM_STR))
            .andExpect(jsonPath("$.[0].dateTo").value(DEFAULT_DATE_TO_STR));
    }
    @Test
    @Transactional
    public void getNonExistingBusinessPartnerContactDetails() throws Exception {
        // Get the businessPartnerContactDetails
        restBusinessPartnerContactDetailsMockMvc.perform(get("/api/businessPartnerContactDetailss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessPartnerContactDetails() throws Exception {
        // Initialize the database
        businessPartnerContactDetailsRepository.saveAndFlush(businessPartnerContactDetails);

        // Update the businessPartnerContactDetails
        businessPartnerContactDetails.setFirstName(UPDATED_FIRST_NAME);
        businessPartnerContactDetails.setLastName(UPDATED_LAST_NAME);
        businessPartnerContactDetails.setEmail(UPDATED_EMAIL);
        businessPartnerContactDetails.setAddress(UPDATED_ADDRESS);
        businessPartnerContactDetails.setDateFrom(UPDATED_DATE_FROM);
        businessPartnerContactDetails.setDateTo(UPDATED_DATE_TO);
        restBusinessPartnerContactDetailsMockMvc.perform(post("/api/businessPartnerContactDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerContactDetails)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerContactDetails in the database
        List<BusinessPartnerContactDetails> businessPartnerContactDetailss = businessPartnerContactDetailsRepository.findAll();
        assertThat(businessPartnerContactDetailss).hasSize(1);
        BusinessPartnerContactDetails testBusinessPartnerContactDetails = businessPartnerContactDetailss.iterator().next();
        assertThat(testBusinessPartnerContactDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBusinessPartnerContactDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBusinessPartnerContactDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBusinessPartnerContactDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBusinessPartnerContactDetails.getDateFrom().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testBusinessPartnerContactDetails.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void deleteBusinessPartnerContactDetails() throws Exception {
        // Initialize the database
        businessPartnerContactDetailsRepository.saveAndFlush(businessPartnerContactDetails);

        // Get the businessPartnerContactDetails
        restBusinessPartnerContactDetailsMockMvc.perform(delete("/api/businessPartnerContactDetailss/{id}", businessPartnerContactDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessPartnerContactDetails> businessPartnerContactDetailss = businessPartnerContactDetailsRepository.findAll();
        assertThat(businessPartnerContactDetailss).hasSize(0);
    }
}
