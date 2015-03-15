package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.Warehouse;
import com.invado.crm.repository.WarehouseRepository;
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
 * REST controller for managing Warehouse.
 */
@RestController
@RequestMapping("/api")
public class WarehouseResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseResource.class);

    @Inject
    private WarehouseRepository warehouseRepository;

    /**
     * POST  /warehouses -> Create a new warehouse.
     */
    @RequestMapping(value = "/warehouses",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Warehouse warehouse) {
        log.debug("REST request to save Warehouse : {}", warehouse);
        warehouseRepository.save(warehouse);
    }

    /**
     * GET  /warehouses -> get all the warehouses.
     */
    @RequestMapping(value = "/warehouses",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Warehouse> getAll() {
        log.debug("REST request to get all Warehouses");
        return warehouseRepository.findAll();
    }

    /**
     * GET  /warehouses/:id -> get the "id" warehouse.
     */
    @RequestMapping(value = "/warehouses/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Warehouse> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Warehouse : {}", id);
        Warehouse warehouse = warehouseRepository.findOne(id);
        if (warehouse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    /**
     * DELETE  /warehouses/:id -> delete the "id" warehouse.
     */
    @RequestMapping(value = "/warehouses/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Warehouse : {}", id);
        warehouseRepository.delete(id);
    }
}
