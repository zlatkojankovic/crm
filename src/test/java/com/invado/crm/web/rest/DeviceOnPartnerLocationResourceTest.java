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
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.invado.crm.Application;
import com.invado.crm.domain.DeviceOnPartnerLocation;
import com.invado.crm.repository.DeviceOnPartnerLocationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceOnPartnerLocationResource REST controller.
 *
 * @see DeviceOnPartnerLocationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeviceOnPartnerLocationResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final LocalDate DEFAULT_DATE_FROM = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_FROM = new LocalDate();

    private static final DateTime DEFAULT_DATE_TO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_TO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_TO_STR = dateTimeFormatter.print(DEFAULT_DATE_TO);
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";

    @Inject
    private DeviceOnPartnerLocationRepository deviceOnPartnerLocationRepository;

    private MockMvc restDeviceOnPartnerLocationMockMvc;

    private DeviceOnPartnerLocation deviceOnPartnerLocation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceOnPartnerLocationResource deviceOnPartnerLocationResource = new DeviceOnPartnerLocationResource();
        ReflectionTestUtils.setField(deviceOnPartnerLocationResource, "deviceOnPartnerLocationRepository", deviceOnPartnerLocationRepository);
        this.restDeviceOnPartnerLocationMockMvc = MockMvcBuilders.standaloneSetup(deviceOnPartnerLocationResource).build();
    }

    @Before
    public void initTest() {
        deviceOnPartnerLocation = new DeviceOnPartnerLocation();
        deviceOnPartnerLocation.setDateFrom(DEFAULT_DATE_FROM);
        deviceOnPartnerLocation.setDateTo(DEFAULT_DATE_TO);
        deviceOnPartnerLocation.setStatus(DEFAULT_STATUS);
        deviceOnPartnerLocation.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createDeviceOnPartnerLocation() throws Exception {
        // Validate the database is empty
        assertThat(deviceOnPartnerLocationRepository.findAll()).hasSize(0);

        // Create the DeviceOnPartnerLocation
        restDeviceOnPartnerLocationMockMvc.perform(post("/api/deviceOnPartnerLocations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deviceOnPartnerLocation)))
                .andExpect(status().isOk());

        // Validate the DeviceOnPartnerLocation in the database
        List<DeviceOnPartnerLocation> deviceOnPartnerLocations = deviceOnPartnerLocationRepository.findAll();
        assertThat(deviceOnPartnerLocations).hasSize(1);
        DeviceOnPartnerLocation testDeviceOnPartnerLocation = deviceOnPartnerLocations.iterator().next();
        assertThat(testDeviceOnPartnerLocation.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testDeviceOnPartnerLocation.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testDeviceOnPartnerLocation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeviceOnPartnerLocation.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllDeviceOnPartnerLocations() throws Exception {
        // Initialize the database
        deviceOnPartnerLocationRepository.saveAndFlush(deviceOnPartnerLocation);

        // Get all the deviceOnPartnerLocations
        restDeviceOnPartnerLocationMockMvc.perform(get("/api/deviceOnPartnerLocations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(deviceOnPartnerLocation.getId().intValue()))
                .andExpect(jsonPath("$.[0].dateFrom").value(DEFAULT_DATE_FROM.toString()))
                .andExpect(jsonPath("$.[0].dateTo").value(DEFAULT_DATE_TO_STR))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getDeviceOnPartnerLocation() throws Exception {
        // Initialize the database
        deviceOnPartnerLocationRepository.saveAndFlush(deviceOnPartnerLocation);

        // Get the deviceOnPartnerLocation
        restDeviceOnPartnerLocationMockMvc.perform(get("/api/deviceOnPartnerLocations/{id}", deviceOnPartnerLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deviceOnPartnerLocation.getId().intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceOnPartnerLocation() throws Exception {
        // Get the deviceOnPartnerLocation
        restDeviceOnPartnerLocationMockMvc.perform(get("/api/deviceOnPartnerLocations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceOnPartnerLocation() throws Exception {
        // Initialize the database
        deviceOnPartnerLocationRepository.saveAndFlush(deviceOnPartnerLocation);

        // Update the deviceOnPartnerLocation
        deviceOnPartnerLocation.setDateFrom(UPDATED_DATE_FROM);
        deviceOnPartnerLocation.setDateTo(UPDATED_DATE_TO);
        deviceOnPartnerLocation.setStatus(UPDATED_STATUS);
        deviceOnPartnerLocation.setComment(UPDATED_COMMENT);
        restDeviceOnPartnerLocationMockMvc.perform(post("/api/deviceOnPartnerLocations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deviceOnPartnerLocation)))
                .andExpect(status().isOk());

        // Validate the DeviceOnPartnerLocation in the database
        List<DeviceOnPartnerLocation> deviceOnPartnerLocations = deviceOnPartnerLocationRepository.findAll();
        assertThat(deviceOnPartnerLocations).hasSize(1);
        DeviceOnPartnerLocation testDeviceOnPartnerLocation = deviceOnPartnerLocations.iterator().next();
        assertThat(testDeviceOnPartnerLocation.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testDeviceOnPartnerLocation.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_TO);
        assertThat(testDeviceOnPartnerLocation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeviceOnPartnerLocation.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteDeviceOnPartnerLocation() throws Exception {
        // Initialize the database
        deviceOnPartnerLocationRepository.saveAndFlush(deviceOnPartnerLocation);

        // Get the deviceOnPartnerLocation
        restDeviceOnPartnerLocationMockMvc.perform(delete("/api/deviceOnPartnerLocations/{id}", deviceOnPartnerLocation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeviceOnPartnerLocation> deviceOnPartnerLocations = deviceOnPartnerLocationRepository.findAll();
        assertThat(deviceOnPartnerLocations).hasSize(0);
    }
}
