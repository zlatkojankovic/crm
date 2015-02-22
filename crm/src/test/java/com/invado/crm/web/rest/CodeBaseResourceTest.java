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
import com.invado.crm.domain.CodeBase;
import com.invado.crm.repository.CodeBaseRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CodeBaseResource REST controller.
 *
 * @see CodeBaseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CodeBaseResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_ENTRY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_ENTRY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_ENTRY_STR = dateTimeFormatter.print(DEFAULT_DATE_ENTRY);

    private static final DateTime DEFAULT_DATE_VALID = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_VALID = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_VALID_STR = dateTimeFormatter.print(DEFAULT_DATE_VALID);

    @Inject
    private CodeBaseRepository codeBaseRepository;

    private MockMvc restCodeBaseMockMvc;

    private CodeBase codeBase;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CodeBaseResource codeBaseResource = new CodeBaseResource();
        ReflectionTestUtils.setField(codeBaseResource, "codeBaseRepository", codeBaseRepository);
        this.restCodeBaseMockMvc = MockMvcBuilders.standaloneSetup(codeBaseResource).build();
    }

    @Before
    public void initTest() {
        codeBase = new CodeBase();
        codeBase.setName(DEFAULT_NAME);
        codeBase.setDateEntry(DEFAULT_DATE_ENTRY);
        codeBase.setDateValid(DEFAULT_DATE_VALID);
    }

    @Test
    @Transactional
    public void createCodeBase() throws Exception {
        // Validate the database is empty
        assertThat(codeBaseRepository.findAll()).hasSize(0);

        // Create the CodeBase
        restCodeBaseMockMvc.perform(post("/api/codeBases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(codeBase)))
                .andExpect(status().isOk());

        // Validate the CodeBase in the database
        List<CodeBase> codeBases = codeBaseRepository.findAll();
        assertThat(codeBases).hasSize(1);
        CodeBase testCodeBase = codeBases.iterator().next();
        assertThat(testCodeBase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCodeBase.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_ENTRY);
        assertThat(testCodeBase.getDateValid().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_VALID);
    }

    @Test
    @Transactional
    public void getAllCodeBases() throws Exception {
        // Initialize the database
        codeBaseRepository.saveAndFlush(codeBase);

        // Get all the codeBases
        restCodeBaseMockMvc.perform(get("/api/codeBases"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(codeBase.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].dateEntry").value(DEFAULT_DATE_ENTRY_STR))
                .andExpect(jsonPath("$.[0].dateValid").value(DEFAULT_DATE_VALID_STR));
    }

    @Test
    @Transactional
    public void getCodeBase() throws Exception {
        // Initialize the database
        codeBaseRepository.saveAndFlush(codeBase);

        // Get the codeBase
        restCodeBaseMockMvc.perform(get("/api/codeBases/{id}", codeBase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(codeBase.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateEntry").value(DEFAULT_DATE_ENTRY_STR))
            .andExpect(jsonPath("$.dateValid").value(DEFAULT_DATE_VALID_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCodeBase() throws Exception {
        // Get the codeBase
        restCodeBaseMockMvc.perform(get("/api/codeBases/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodeBase() throws Exception {
        // Initialize the database
        codeBaseRepository.saveAndFlush(codeBase);

        // Update the codeBase
        codeBase.setName(UPDATED_NAME);
        codeBase.setDateEntry(UPDATED_DATE_ENTRY);
        codeBase.setDateValid(UPDATED_DATE_VALID);
        restCodeBaseMockMvc.perform(post("/api/codeBases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(codeBase)))
                .andExpect(status().isOk());

        // Validate the CodeBase in the database
        List<CodeBase> codeBases = codeBaseRepository.findAll();
        assertThat(codeBases).hasSize(1);
        CodeBase testCodeBase = codeBases.iterator().next();
        assertThat(testCodeBase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCodeBase.getDateEntry().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_ENTRY);
        assertThat(testCodeBase.getDateValid().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_VALID);
    }

    @Test
    @Transactional
    public void deleteCodeBase() throws Exception {
        // Initialize the database
        codeBaseRepository.saveAndFlush(codeBase);

        // Get the codeBase
        restCodeBaseMockMvc.perform(delete("/api/codeBases/{id}", codeBase.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CodeBase> codeBases = codeBaseRepository.findAll();
        assertThat(codeBases).hasSize(0);
    }
}
