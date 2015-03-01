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
import com.invado.crm.domain.ClassificationType;
import com.invado.crm.repository.ClassificationTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClassificationTypeResource REST controller.
 *
 * @see ClassificationTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClassificationTypeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_MANDATORY = 0;
    private static final Integer UPDATED_MANDATORY = 1;
    private static final String DEFAULT_OBJECT_CLASSIFICATION_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_OBJECT_CLASSIFICATION_TYPE = "UPDATED_TEXT";

    @Inject
    private ClassificationTypeRepository classificationTypeRepository;

    private MockMvc restClassificationTypeMockMvc;

    private ClassificationType classificationType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassificationTypeResource classificationTypeResource = new ClassificationTypeResource();
        ReflectionTestUtils.setField(classificationTypeResource, "classificationTypeRepository", classificationTypeRepository);
        this.restClassificationTypeMockMvc = MockMvcBuilders.standaloneSetup(classificationTypeResource).build();
    }

    @Before
    public void initTest() {
        classificationType = new ClassificationType();
        classificationType.setName(DEFAULT_NAME);
        classificationType.setMandatory(DEFAULT_MANDATORY);
        classificationType.setObjectClassificationType(DEFAULT_OBJECT_CLASSIFICATION_TYPE);
    }

    @Test
    @Transactional
    public void createClassificationType() throws Exception {
        // Validate the database is empty
        assertThat(classificationTypeRepository.findAll()).hasSize(0);

        // Create the ClassificationType
        restClassificationTypeMockMvc.perform(post("/api/classificationTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classificationType)))
                .andExpect(status().isOk());

        // Validate the ClassificationType in the database
        List<ClassificationType> classificationTypes = classificationTypeRepository.findAll();
        assertThat(classificationTypes).hasSize(1);
        ClassificationType testClassificationType = classificationTypes.iterator().next();
        assertThat(testClassificationType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassificationType.getMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testClassificationType.getObjectClassificationType()).isEqualTo(DEFAULT_OBJECT_CLASSIFICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllClassificationTypes() throws Exception {
        // Initialize the database
        classificationTypeRepository.saveAndFlush(classificationType);

        // Get all the classificationTypes
        restClassificationTypeMockMvc.perform(get("/api/classificationTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(classificationType.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].mandatory").value(DEFAULT_MANDATORY))
                .andExpect(jsonPath("$.[0].objectClassificationType").value(DEFAULT_OBJECT_CLASSIFICATION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getClassificationType() throws Exception {
        // Initialize the database
        classificationTypeRepository.saveAndFlush(classificationType);

        // Get the classificationType
        restClassificationTypeMockMvc.perform(get("/api/classificationTypes/{id}", classificationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classificationType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY))
            .andExpect(jsonPath("$.objectClassificationType").value(DEFAULT_OBJECT_CLASSIFICATION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassificationType() throws Exception {
        // Get the classificationType
        restClassificationTypeMockMvc.perform(get("/api/classificationTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassificationType() throws Exception {
        // Initialize the database
        classificationTypeRepository.saveAndFlush(classificationType);

        // Update the classificationType
        classificationType.setName(UPDATED_NAME);
        classificationType.setMandatory(UPDATED_MANDATORY);
        classificationType.setObjectClassificationType(UPDATED_OBJECT_CLASSIFICATION_TYPE);
        restClassificationTypeMockMvc.perform(post("/api/classificationTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classificationType)))
                .andExpect(status().isOk());

        // Validate the ClassificationType in the database
        List<ClassificationType> classificationTypes = classificationTypeRepository.findAll();
        assertThat(classificationTypes).hasSize(1);
        ClassificationType testClassificationType = classificationTypes.iterator().next();
        assertThat(testClassificationType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassificationType.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testClassificationType.getObjectClassificationType()).isEqualTo(UPDATED_OBJECT_CLASSIFICATION_TYPE);
    }

    @Test
    @Transactional
    public void deleteClassificationType() throws Exception {
        // Initialize the database
        classificationTypeRepository.saveAndFlush(classificationType);

        // Get the classificationType
        restClassificationTypeMockMvc.perform(delete("/api/classificationTypes/{id}", classificationType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassificationType> classificationTypes = classificationTypeRepository.findAll();
        assertThat(classificationTypes).hasSize(0);
    }
}
