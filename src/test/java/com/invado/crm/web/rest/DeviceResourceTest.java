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
import com.invado.crm.domain.Device;
import com.invado.crm.repository.DeviceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceResource REST controller.
 *
 * @see DeviceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeviceResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_CUSTOM_ID = "SAMPLE_TEXT";
    private static final String UPDATED_CUSTOM_ID = "UPDATED_TEXT";
    private static final String DEFAULT_SERIAL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_SERIAL_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_FIRMSARE_VERSION = "SAMPLE_TEXT";
    private static final String UPDATED_FIRMSARE_VERSION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ENTRY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ENTRY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ENTRY_STR = dateTimeFormatter.print(DEFAULT_DATE_ENTRY);

    private static final DateTime DEFAULT_DATE_VALID = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_VALID = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_VALID_STR = dateTimeFormatter.print(DEFAULT_DATE_VALID);

    @Inject
    private DeviceRepository deviceRepository;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceResource deviceResource = new DeviceResource();
        ReflectionTestUtils.setField(deviceResource, "deviceRepository", deviceRepository);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource).build();
    }

    @Before
    public void initTest() {
        device = new Device();
        device.setCustomId(DEFAULT_CUSTOM_ID);
        device.setSerialNumber(DEFAULT_SERIAL_NUMBER);
        device.setStatus(DEFAULT_STATUS);
        device.setFirmsareVersion(DEFAULT_FIRMSARE_VERSION);
        device.setDateEntry(DEFAULT_DATE_ENTRY);
        device.setDateValid(DEFAULT_DATE_VALID);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        // Validate the database is empty
        assertThat(deviceRepository.findAll()).hasSize(0);

        // Create the Device
        restDeviceMockMvc.perform(post("/api/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(1);
        Device testDevice = devices.iterator().next();
        assertThat(testDevice.getCustomId()).isEqualTo(DEFAULT_CUSTOM_ID);
        assertThat(testDevice.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDevice.getFirmsareVersion()).isEqualTo(DEFAULT_FIRMSARE_VERSION);
        assertThat(testDevice.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ENTRY);
        assertThat(testDevice.getDateValid().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_VALID);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the devices
        restDeviceMockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(device.getId().intValue()))
                .andExpect(jsonPath("$.[0].customId").value(DEFAULT_CUSTOM_ID.toString()))
                .andExpect(jsonPath("$.[0].serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].firmsareVersion").value(DEFAULT_FIRMSARE_VERSION.toString()))
                .andExpect(jsonPath("$.[0].dateEntry").value(DEFAULT_DATE_ENTRY_STR))
                .andExpect(jsonPath("$.[0].dateValid").value(DEFAULT_DATE_VALID_STR));
    }

    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.customId").value(DEFAULT_CUSTOM_ID.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.firmsareVersion").value(DEFAULT_FIRMSARE_VERSION.toString()))
            .andExpect(jsonPath("$.dateEntry").value(DEFAULT_DATE_ENTRY_STR))
            .andExpect(jsonPath("$.dateValid").value(DEFAULT_DATE_VALID_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Update the device
        device.setCustomId(UPDATED_CUSTOM_ID);
        device.setSerialNumber(UPDATED_SERIAL_NUMBER);
        device.setStatus(UPDATED_STATUS);
        device.setFirmsareVersion(UPDATED_FIRMSARE_VERSION);
        device.setDateEntry(UPDATED_DATE_ENTRY);
        device.setDateValid(UPDATED_DATE_VALID);
        restDeviceMockMvc.perform(post("/api/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(1);
        Device testDevice = devices.iterator().next();
        assertThat(testDevice.getCustomId()).isEqualTo(UPDATED_CUSTOM_ID);
        assertThat(testDevice.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDevice.getFirmsareVersion()).isEqualTo(UPDATED_FIRMSARE_VERSION);
        assertThat(testDevice.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ENTRY);
        assertThat(testDevice.getDateValid().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_VALID);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(0);
    }
}
