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
import com.invado.crm.domain.BusinessPartnerDetails;
import com.invado.crm.repository.BusinessPartnerDetailsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessPartnerDetailsResource REST controller.
 *
 * @see BusinessPartnerDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusinessPartnerDetailsResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_CONTENT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTENT = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_FROM = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_FROM = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_FROM_STR = dateTimeFormatter.print(DEFAULT_DATE_FROM);

    private static final DateTime DEFAULT_DATE_TO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_TO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_TO_STR = dateTimeFormatter.print(DEFAULT_DATE_TO);
    private static final String DEFAULT_CONTENT_VERSION = "SAMPLE_TEXT";
    private static final String UPDATED_CONTENT_VERSION = "UPDATED_TEXT";

    @Inject
    private BusinessPartnerDetailsRepository businessPartnerDetailsRepository;

    private MockMvc restBusinessPartnerDetailsMockMvc;

    private BusinessPartnerDetails businessPartnerDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessPartnerDetailsResource businessPartnerDetailsResource = new BusinessPartnerDetailsResource();
        ReflectionTestUtils.setField(businessPartnerDetailsResource, "businessPartnerDetailsRepository", businessPartnerDetailsRepository);
        this.restBusinessPartnerDetailsMockMvc = MockMvcBuilders.standaloneSetup(businessPartnerDetailsResource).build();
    }

    @Before
    public void initTest() {
        businessPartnerDetails = new BusinessPartnerDetails();
        businessPartnerDetails.setContent(DEFAULT_CONTENT);
        businessPartnerDetails.setDateFrom(DEFAULT_DATE_FROM);
        businessPartnerDetails.setDateTo(DEFAULT_DATE_TO);
        businessPartnerDetails.setContentVersion(DEFAULT_CONTENT_VERSION);
    }

    @Test
    @Transactional
    public void createBusinessPartnerDetails() throws Exception {
        // Validate the database is empty
        assertThat(businessPartnerDetailsRepository.findAll()).hasSize(0);

        // Create the BusinessPartnerDetails
        restBusinessPartnerDetailsMockMvc.perform(post("/api/businessPartnerDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerDetails)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerDetails in the database
        List<BusinessPartnerDetails> businessPartnerDetailss = businessPartnerDetailsRepository.findAll();
        assertThat(businessPartnerDetailss).hasSize(1);
        BusinessPartnerDetails testBusinessPartnerDetails = businessPartnerDetailss.iterator().next();
        assertThat(testBusinessPartnerDetails.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBusinessPartnerDetails.getDateFrom().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testBusinessPartnerDetails.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testBusinessPartnerDetails.getContentVersion()).isEqualTo(DEFAULT_CONTENT_VERSION);
    }

    @Test
    @Transactional
    public void getAllBusinessPartnerDetailss() throws Exception {
        // Initialize the database
        businessPartnerDetailsRepository.saveAndFlush(businessPartnerDetails);

        // Get all the businessPartnerDetailss
        restBusinessPartnerDetailsMockMvc.perform(get("/api/businessPartnerDetailss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(businessPartnerDetails.getId().intValue()))
                .andExpect(jsonPath("$.[0].content").value(DEFAULT_CONTENT.toString()))
                .andExpect(jsonPath("$.[0].dateFrom").value(DEFAULT_DATE_FROM_STR))
                .andExpect(jsonPath("$.[0].dateTo").value(DEFAULT_DATE_TO_STR))
                .andExpect(jsonPath("$.[0].contentVersion").value(DEFAULT_CONTENT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getBusinessPartnerDetails() throws Exception {
        // Initialize the database
        businessPartnerDetailsRepository.saveAndFlush(businessPartnerDetails);

        // Get the businessPartnerDetails
        restBusinessPartnerDetailsMockMvc.perform(get("/api/businessPartnerDetailss/{id}", businessPartnerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(businessPartnerDetails.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM_STR))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO_STR))
            .andExpect(jsonPath("$.contentVersion").value(DEFAULT_CONTENT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessPartnerDetails() throws Exception {
        // Get the businessPartnerDetails
        restBusinessPartnerDetailsMockMvc.perform(get("/api/businessPartnerDetailss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessPartnerDetails() throws Exception {
        // Initialize the database
        businessPartnerDetailsRepository.saveAndFlush(businessPartnerDetails);

        // Update the businessPartnerDetails
        businessPartnerDetails.setContent(UPDATED_CONTENT);
        businessPartnerDetails.setDateFrom(UPDATED_DATE_FROM);
        businessPartnerDetails.setDateTo(UPDATED_DATE_TO);
        businessPartnerDetails.setContentVersion(UPDATED_CONTENT_VERSION);
        restBusinessPartnerDetailsMockMvc.perform(post("/api/businessPartnerDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerDetails)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerDetails in the database
        List<BusinessPartnerDetails> businessPartnerDetailss = businessPartnerDetailsRepository.findAll();
        assertThat(businessPartnerDetailss).hasSize(1);
        BusinessPartnerDetails testBusinessPartnerDetails = businessPartnerDetailss.iterator().next();
        assertThat(testBusinessPartnerDetails.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBusinessPartnerDetails.getDateFrom().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testBusinessPartnerDetails.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_TO);
        assertThat(testBusinessPartnerDetails.getContentVersion()).isEqualTo(UPDATED_CONTENT_VERSION);
    }

    @Test
    @Transactional
    public void deleteBusinessPartnerDetails() throws Exception {
        // Initialize the database
        businessPartnerDetailsRepository.saveAndFlush(businessPartnerDetails);

        // Get the businessPartnerDetails
        restBusinessPartnerDetailsMockMvc.perform(delete("/api/businessPartnerDetailss/{id}", businessPartnerDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessPartnerDetails> businessPartnerDetailss = businessPartnerDetailsRepository.findAll();
        assertThat(businessPartnerDetailss).hasSize(0);
    }
}
