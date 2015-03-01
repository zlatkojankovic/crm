package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.Territory;
import com.invado.crm.repository.TerritoryRepository;
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
 * REST controller for managing Territory.
 */
@RestController
@RequestMapping("/api")
public class TerritoryResource {

    private final Logger log = LoggerFactory.getLogger(TerritoryResource.class);

    @Inject
    private TerritoryRepository territoryRepository;

    /**
     * POST  /territorys -> Create a new territory.
     */
    @RequestMapping(value = "/territorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Territory territory) {
        log.debug("REST request to save Territory : {}", territory);
        territoryRepository.save(territory);
    }

    /**
     * GET  /territorys -> get all the territorys.
     */
    @RequestMapping(value = "/territorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Territory> getAll() {
        log.debug("REST request to get all Territorys");
        return territoryRepository.findAll();
    }

    /**
     * GET  /territorys/:id -> get the "id" territory.
     */
    @RequestMapping(value = "/territorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Territory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Territory : {}", id);
        Territory territory = territoryRepository.findOne(id);
        if (territory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(territory, HttpStatus.OK);
    }

    /**
     * DELETE  /territorys/:id -> delete the "id" territory.
     */
    @RequestMapping(value = "/territorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Territory : {}", id);
        territoryRepository.delete(id);
    }
}
