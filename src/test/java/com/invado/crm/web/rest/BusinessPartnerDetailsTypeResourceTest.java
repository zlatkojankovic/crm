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
import com.invado.crm.domain.BusinessPartnerDetailsType;
import com.invado.crm.repository.BusinessPartnerDetailsTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessPartnerDetailsTypeResource REST controller.
 *
 * @see BusinessPartnerDetailsTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusinessPartnerDetailsTypeResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ENTRY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ENTRY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ENTRY_STR = dateTimeFormatter.print(DEFAULT_DATE_ENTRY);

    @Inject
    private BusinessPartnerDetailsTypeRepository businessPartnerDetailsTypeRepository;

    private MockMvc restBusinessPartnerDetailsTypeMockMvc;

    private BusinessPartnerDetailsType businessPartnerDetailsType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessPartnerDetailsTypeResource businessPartnerDetailsTypeResource = new BusinessPartnerDetailsTypeResource();
        ReflectionTestUtils.setField(businessPartnerDetailsTypeResource, "businessPartnerDetailsTypeRepository", businessPartnerDetailsTypeRepository);
        this.restBusinessPartnerDetailsTypeMockMvc = MockMvcBuilders.standaloneSetup(businessPartnerDetailsTypeResource).build();
    }

    @Before
    public void initTest() {
        businessPartnerDetailsType = new BusinessPartnerDetailsType();
        businessPartnerDetailsType.setName(DEFAULT_NAME);
        businessPartnerDetailsType.setDescription(DEFAULT_DESCRIPTION);
        businessPartnerDetailsType.setDateEntry(DEFAULT_DATE_ENTRY);
    }

    @Test
    @Transactional
    public void createBusinessPartnerDetailsType() throws Exception {
        // Validate the database is empty
        assertThat(businessPartnerDetailsTypeRepository.findAll()).hasSize(0);

        // Create the BusinessPartnerDetailsType
        restBusinessPartnerDetailsTypeMockMvc.perform(post("/api/businessPartnerDetailsTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerDetailsType)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerDetailsType in the database
        List<BusinessPartnerDetailsType> businessPartnerDetailsTypes = businessPartnerDetailsTypeRepository.findAll();
        assertThat(businessPartnerDetailsTypes).hasSize(1);
        BusinessPartnerDetailsType testBusinessPartnerDetailsType = businessPartnerDetailsTypes.iterator().next();
        assertThat(testBusinessPartnerDetailsType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessPartnerDetailsType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBusinessPartnerDetailsType.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ENTRY);
    }

    @Test
    @Transactional
    public void getAllBusinessPartnerDetailsTypes() throws Exception {
        // Initialize the database
        businessPartnerDetailsTypeRepository.saveAndFlush(businessPartnerDetailsType);

        // Get all the businessPartnerDetailsTypes
        restBusinessPartnerDetailsTypeMockMvc.perform(get("/api/businessPartnerDetailsTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(businessPartnerDetailsType.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].dateEntry").value(DEFAULT_DATE_ENTRY_STR));
    }

    @Test
    @Transactional
    public void getBusinessPartnerDetailsType() throws Exception {
        // Initialize the database
        businessPartnerDetailsTypeRepository.saveAndFlush(businessPartnerDetailsType);

        // Get the businessPartnerDetailsType
        restBusinessPartnerDetailsTypeMockMvc.perform(get("/api/businessPartnerDetailsTypes/{id}", businessPartnerDetailsType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(businessPartnerDetailsType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateEntry").value(DEFAULT_DATE_ENTRY_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessPartnerDetailsType() throws Exception {
        // Get the businessPartnerDetailsType
        restBusinessPartnerDetailsTypeMockMvc.perform(get("/api/businessPartnerDetailsTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessPartnerDetailsType() throws Exception {
        // Initialize the database
        businessPartnerDetailsTypeRepository.saveAndFlush(businessPartnerDetailsType);

        // Update the businessPartnerDetailsType
        businessPartnerDetailsType.setName(UPDATED_NAME);
        businessPartnerDetailsType.setDescription(UPDATED_DESCRIPTION);
        businessPartnerDetailsType.setDateEntry(UPDATED_DATE_ENTRY);
        restBusinessPartnerDetailsTypeMockMvc.perform(post("/api/businessPartnerDetailsTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(businessPartnerDetailsType)))
                .andExpect(status().isOk());

        // Validate the BusinessPartnerDetailsType in the database
        List<BusinessPartnerDetailsType> businessPartnerDetailsTypes = businessPartnerDetailsTypeRepository.findAll();
        assertThat(businessPartnerDetailsTypes).hasSize(1);
        BusinessPartnerDetailsType testBusinessPartnerDetailsType = businessPartnerDetailsTypes.iterator().next();
        assertThat(testBusinessPartnerDetailsType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessPartnerDetailsType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBusinessPartnerDetailsType.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ENTRY);
    }

    @Test
    @Transactional
    public void deleteBusinessPartnerDetailsType() throws Exception {
        // Initialize the database
        businessPartnerDetailsTypeRepository.saveAndFlush(businessPartnerDetailsType);

        // Get the businessPartnerDetailsType
        restBusinessPartnerDetailsTypeMockMvc.perform(delete("/api/businessPartnerDetailsTypes/{id}", businessPartnerDetailsType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessPartnerDetailsType> businessPartnerDetailsTypes = businessPartnerDetailsTypeRepository.findAll();
        assertThat(businessPartnerDetailsTypes).hasSize(0);
    }
}
