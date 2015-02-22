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
import com.invado.crm.domain.Person;
import com.invado.crm.repository.PersonRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersonResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LAST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_DEFAULT_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_NATIONAL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_NATIONAL_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_PASSWORD = "SAMPLE_TEXT";
    private static final String UPDATED_PASSWORD = "UPDATED_TEXT";
    private static final String DEFAULT_USERNAME = "SAMPLE_TEXT";
    private static final String UPDATED_USERNAME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ENTRY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ENTRY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ENTRY_STR = dateTimeFormatter.print(DEFAULT_DATE_ENTRY);

    private static final DateTime DEFAULT_DATE_VALID = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_VALID = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_VALID_STR = dateTimeFormatter.print(DEFAULT_DATE_VALID);

    @Inject
    private PersonRepository personRepository;

    private MockMvc restPersonMockMvc;

    private Person person;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personRepository", personRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource).build();
    }

    @Before
    public void initTest() {
        person = new Person();
        person.setFirstName(DEFAULT_FIRST_NAME);
        person.setLastName(DEFAULT_LAST_NAME);
        person.setDefaultAddress(DEFAULT_DEFAULT_ADDRESS);
        person.setNationalNumber(DEFAULT_NATIONAL_NUMBER);
        person.setPassword(DEFAULT_PASSWORD);
        person.setUsername(DEFAULT_USERNAME);
        person.setDateEntry(DEFAULT_DATE_ENTRY);
        person.setDateValid(DEFAULT_DATE_VALID);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        // Validate the database is empty
        assertThat(personRepository.findAll()).hasSize(0);

        // Create the Person
        restPersonMockMvc.perform(post("/api/persons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(1);
        Person testPerson = persons.iterator().next();
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPerson.getDefaultAddress()).isEqualTo(DEFAULT_DEFAULT_ADDRESS);
        assertThat(testPerson.getNationalNumber()).isEqualTo(DEFAULT_NATIONAL_NUMBER);
        assertThat(testPerson.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPerson.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testPerson.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ENTRY);
        assertThat(testPerson.getDateValid().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_VALID);
    }

    @Test
    @Transactional
    public void getAllPersons() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the persons
        restPersonMockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(person.getId().intValue()))
                .andExpect(jsonPath("$.[0].firstName").value(DEFAULT_FIRST_NAME.toString()))
                .andExpect(jsonPath("$.[0].lastName").value(DEFAULT_LAST_NAME.toString()))
                .andExpect(jsonPath("$.[0].defaultAddress").value(DEFAULT_DEFAULT_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].nationalNumber").value(DEFAULT_NATIONAL_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].password").value(DEFAULT_PASSWORD.toString()))
                .andExpect(jsonPath("$.[0].username").value(DEFAULT_USERNAME.toString()))
                .andExpect(jsonPath("$.[0].dateEntry").value(DEFAULT_DATE_ENTRY_STR))
                .andExpect(jsonPath("$.[0].dateValid").value(DEFAULT_DATE_VALID_STR));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/persons/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.defaultAddress").value(DEFAULT_DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.nationalNumber").value(DEFAULT_NATIONAL_NUMBER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.dateEntry").value(DEFAULT_DATE_ENTRY_STR))
            .andExpect(jsonPath("$.dateValid").value(DEFAULT_DATE_VALID_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/persons/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Update the person
        person.setFirstName(UPDATED_FIRST_NAME);
        person.setLastName(UPDATED_LAST_NAME);
        person.setDefaultAddress(UPDATED_DEFAULT_ADDRESS);
        person.setNationalNumber(UPDATED_NATIONAL_NUMBER);
        person.setPassword(UPDATED_PASSWORD);
        person.setUsername(UPDATED_USERNAME);
        person.setDateEntry(UPDATED_DATE_ENTRY);
        person.setDateValid(UPDATED_DATE_VALID);
        restPersonMockMvc.perform(post("/api/persons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(1);
        Person testPerson = persons.iterator().next();
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPerson.getDefaultAddress()).isEqualTo(UPDATED_DEFAULT_ADDRESS);
        assertThat(testPerson.getNationalNumber()).isEqualTo(UPDATED_NATIONAL_NUMBER);
        assertThat(testPerson.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPerson.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPerson.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ENTRY);
        assertThat(testPerson.getDateValid().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_VALID);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(delete("/api/persons/{id}", person.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> persons = personRepository.findAll();
        assertThat(persons).hasSize(0);
    }
}
