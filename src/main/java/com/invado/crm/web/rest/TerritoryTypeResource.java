package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.TerritoryType;
import com.invado.crm.repository.TerritoryTypeRepository;
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
 * REST controller for managing TerritoryType.
 */
@RestController
@RequestMapping("/api")
public class TerritoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(TerritoryTypeResource.class);

    @Inject
    private TerritoryTypeRepository territoryTypeRepository;

    /**
     * POST  /territoryTypes -> Create a new territoryType.
     */
    @RequestMapping(value = "/territoryTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody TerritoryType territoryType) {
        log.debug("REST request to save TerritoryType : {}", territoryType);
        territoryTypeRepository.save(territoryType);
    }

    /**
     * GET  /territoryTypes -> get all the territoryTypes.
     */
    @RequestMapping(value = "/territoryTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TerritoryType> getAll() {
        log.debug("REST request to get all TerritoryTypes");
        return territoryTypeRepository.findAll();
    }

    /**
     * GET  /territoryTypes/:id -> get the "id" territoryType.
     */
    @RequestMapping(value = "/territoryTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TerritoryType> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get TerritoryType : {}", id);
        TerritoryType territoryType = territoryTypeRepository.findOne(id);
        if (territoryType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(territoryType, HttpStatus.OK);
    }

    /**
     * DELETE  /territoryTypes/:id -> delete the "id" territoryType.
     */
    @RequestMapping(value = "/territoryTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete TerritoryType : {}", id);
        territoryTypeRepository.delete(id);
    }
}
