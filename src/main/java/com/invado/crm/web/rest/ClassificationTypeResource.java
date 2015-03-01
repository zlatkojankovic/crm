package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.ClassificationType;
import com.invado.crm.repository.ClassificationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing ClassificationType.
 */
@RestController
@RequestMapping("/api")
public class ClassificationTypeResource {

    private final Logger log = LoggerFactory.getLogger(ClassificationTypeResource.class);

    @Inject
    private ClassificationTypeRepository classificationTypeRepository;

    /**
     * POST  /classificationTypes -> Create a new classificationType.
     */
    @RequestMapping(value = "/classificationTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody ClassificationType classificationType) {
        log.debug("REST request to save ClassificationType : {}", classificationType);
        classificationTypeRepository.save(classificationType);
    }

    /**
     * GET  /classificationTypes -> get all the classificationTypes.
     */
    @RequestMapping(value = "/classificationTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClassificationType> getAll() {
        log.debug("REST request to get all ClassificationTypes");
        return classificationTypeRepository.findAll();
    }

    /**
     * GET  /classificationTypes/:id -> get the "id" classificationType.
     */
    @RequestMapping(value = "/classificationTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassificationType> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ClassificationType : {}", id);
        ClassificationType classificationType = classificationTypeRepository.findOne(id);
        if (classificationType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(classificationType, HttpStatus.OK);
    }

    /**
     * DELETE  /classificationTypes/:id -> delete the "id" classificationType.
     */
    @RequestMapping(value = "/classificationTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ClassificationType : {}", id);
        classificationTypeRepository.delete(id);
    }
}
