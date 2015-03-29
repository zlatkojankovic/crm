package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.UnitOfMeasure;
import com.invado.crm.repository.UnitOfMeasureRepository;
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
 * REST controller for managing UnitOfMeasure.
 */
@RestController
@RequestMapping("/api")
public class UnitOfMeasureResource {

    private final Logger log = LoggerFactory.getLogger(UnitOfMeasureResource.class);

    @Inject
    private UnitOfMeasureRepository unitOfMeasureRepository;

    /**
     * POST  /unitOfMeasures -> Create a new unitOfMeasure.
     */
    @RequestMapping(value = "/unitOfMeasures",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody UnitOfMeasure unitOfMeasure) {
        log.debug("REST request to save UnitOfMeasure : {}", unitOfMeasure);
        unitOfMeasureRepository.save(unitOfMeasure);
    }

    /**
     * GET  /unitOfMeasures -> get all the unitOfMeasures.
     */
    @RequestMapping(value = "/unitOfMeasures",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UnitOfMeasure> getAll() {
        log.debug("REST request to get all UnitOfMeasures");
        return unitOfMeasureRepository.findAll();
    }

    /**
     * GET  /unitOfMeasures/:id -> get the "id" unitOfMeasure.
     */
    @RequestMapping(value = "/unitOfMeasures/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UnitOfMeasure> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get UnitOfMeasure : {}", id);
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findOne(id);
        if (unitOfMeasure == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(unitOfMeasure, HttpStatus.OK);
    }

    /**
     * DELETE  /unitOfMeasures/:id -> delete the "id" unitOfMeasure.
     */
    @RequestMapping(value = "/unitOfMeasures/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UnitOfMeasure : {}", id);
        unitOfMeasureRepository.delete(id);
    }
}
