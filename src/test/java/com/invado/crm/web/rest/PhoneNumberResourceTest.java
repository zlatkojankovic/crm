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
import com.invado.crm.domain.PhoneNumber;
import com.invado.crm.repository.PhoneNumberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PhoneNumberResource REST controller.
 *
 * @see PhoneNumberResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PhoneNumberResourceTest {

    private static final String DEFAULT_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";

    @Inject
    private PhoneNumberRepository phoneNumberRepository;

    private MockMvc restPhoneNumberMockMvc;

    private PhoneNumber phoneNumber;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhoneNumberResource phoneNumberResource = new PhoneNumberResource();
        ReflectionTestUtils.setField(phoneNumberResource, "phoneNumberRepository", phoneNumberRepository);
        this.restPhoneNumberMockMvc = MockMvcBuilders.standaloneSetup(phoneNumberResource).build();
    }

    @Before
    public void initTest() {
        phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(DEFAULT_NUMBER);
        phoneNumber.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPhoneNumber() throws Exception {
        // Validate the database is empty
        assertThat(phoneNumberRepository.findAll()).hasSize(0);

        // Create the PhoneNumber
        restPhoneNumberMockMvc.perform(post("/api/phoneNumbers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
                .andExpect(status().isOk());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(1);
        PhoneNumber testPhoneNumber = phoneNumbers.iterator().next();
        assertThat(testPhoneNumber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPhoneNumber.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPhoneNumbers() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get all the phoneNumbers
        restPhoneNumberMockMvc.perform(get("/api/phoneNumbers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(phoneNumber.getId().intValue()))
                .andExpect(jsonPath("$.[0].number").value(DEFAULT_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getPhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phoneNumbers/{id}", phoneNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(phoneNumber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumber() throws Exception {
        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(get("/api/phoneNumbers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Update the phoneNumber
        phoneNumber.setNumber(UPDATED_NUMBER);
        phoneNumber.setType(UPDATED_TYPE);
        restPhoneNumberMockMvc.perform(post("/api/phoneNumbers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(phoneNumber)))
                .andExpect(status().isOk());

        // Validate the PhoneNumber in the database
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(1);
        PhoneNumber testPhoneNumber = phoneNumbers.iterator().next();
        assertThat(testPhoneNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPhoneNumber.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deletePhoneNumber() throws Exception {
        // Initialize the database
        phoneNumberRepository.saveAndFlush(phoneNumber);

        // Get the phoneNumber
        restPhoneNumberMockMvc.perform(delete("/api/phoneNumbers/{id}", phoneNumber.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        assertThat(phoneNumbers).hasSize(0);
    }
}
