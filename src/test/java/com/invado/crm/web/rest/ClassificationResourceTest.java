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
import com.invado.crm.domain.Classification;
import com.invado.crm.repository.ClassificationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClassificationResource REST controller.
 *
 * @see ClassificationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClassificationResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CLASSIFICATION_SEGMENT = "SAMPLE_TEXT";
    private static final String UPDATED_CLASSIFICATION_SEGMENT = "UPDATED_TEXT";

    @Inject
    private ClassificationRepository classificationRepository;

    private MockMvc restClassificationMockMvc;

    private Classification classification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassificationResource classificationResource = new ClassificationResource();
        ReflectionTestUtils.setField(classificationResource, "classificationRepository", classificationRepository);
        this.restClassificationMockMvc = MockMvcBuilders.standaloneSetup(classificationResource).build();
    }

    @Before
    public void initTest() {
        classification = new Classification();
        classification.setName(DEFAULT_NAME);
        classification.setClassificationSegment(DEFAULT_CLASSIFICATION_SEGMENT);
    }

    @Test
    @Transactional
    public void createClassification() throws Exception {
        // Validate the database is empty
        assertThat(classificationRepository.findAll()).hasSize(0);

        // Create the Classification
        restClassificationMockMvc.perform(post("/api/classifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classification)))
                .andExpect(status().isOk());

        // Validate the Classification in the database
        List<Classification> classifications = classificationRepository.findAll();
        assertThat(classifications).hasSize(1);
        Classification testClassification = classifications.iterator().next();
        assertThat(testClassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassification.getClassificationSegment()).isEqualTo(DEFAULT_CLASSIFICATION_SEGMENT);
    }

    @Test
    @Transactional
    public void getAllClassifications() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);

        // Get all the classifications
        restClassificationMockMvc.perform(get("/api/classifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(classification.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].classificationSegment").value(DEFAULT_CLASSIFICATION_SEGMENT.toString()));
    }

    @Test
    @Transactional
    public void getClassification() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);

        // Get the classification
        restClassificationMockMvc.perform(get("/api/classifications/{id}", classification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classification.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.classificationSegment").value(DEFAULT_CLASSIFICATION_SEGMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassification() throws Exception {
        // Get the classification
        restClassificationMockMvc.perform(get("/api/classifications/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassification() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);

        // Update the classification
        classification.setName(UPDATED_NAME);
        classification.setClassificationSegment(UPDATED_CLASSIFICATION_SEGMENT);
        restClassificationMockMvc.perform(post("/api/classifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classification)))
                .andExpect(status().isOk());

        // Validate the Classification in the database
        List<Classification> classifications = classificationRepository.findAll();
        assertThat(classifications).hasSize(1);
        Classification testClassification = classifications.iterator().next();
        assertThat(testClassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassification.getClassificationSegment()).isEqualTo(UPDATED_CLASSIFICATION_SEGMENT);
    }

    @Test
    @Transactional
    public void deleteClassification() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);

        // Get the classification
        restClassificationMockMvc.perform(delete("/api/classifications/{id}", classification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Classification> classifications = classificationRepository.findAll();
        assertThat(classifications).hasSize(0);
    }
}
