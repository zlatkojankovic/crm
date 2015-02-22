package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.Classification;
import com.invado.crm.repository.ClassificationRepository;
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
 * REST controller for managing Classification.
 */
@RestController
@RequestMapping("/api")
public class ClassificationResource {

    private final Logger log = LoggerFactory.getLogger(ClassificationResource.class);

    @Inject
    private ClassificationRepository classificationRepository;

    /**
     * POST  /classifications -> Create a new classification.
     */
    @RequestMapping(value = "/classifications",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Classification classification) {
        log.debug("REST request to save Classification : {}", classification);
        classificationRepository.save(classification);
    }

    /**
     * GET  /classifications -> get all the classifications.
     */
    @RequestMapping(value = "/classifications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Classification> getAll() {
        log.debug("REST request to get all Classifications");
        return classificationRepository.findAll();
    }

    /**
     * GET  /classifications/:id -> get the "id" classification.
     */
    @RequestMapping(value = "/classifications/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classification> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Classification : {}", id);
        Classification classification = classificationRepository.findOne(id);
        if (classification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(classification, HttpStatus.OK);
    }

    /**
     * DELETE  /classifications/:id -> delete the "id" classification.
     */
    @RequestMapping(value = "/classifications/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Classification : {}", id);
        classificationRepository.delete(id);
    }
}
