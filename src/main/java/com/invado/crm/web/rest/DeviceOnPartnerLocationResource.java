package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.DeviceOnPartnerLocation;
import com.invado.crm.repository.DeviceOnPartnerLocationRepository;
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
 * REST controller for managing DeviceOnPartnerLocation.
 */
@RestController
@RequestMapping("/api")
public class DeviceOnPartnerLocationResource {

    private final Logger log = LoggerFactory.getLogger(DeviceOnPartnerLocationResource.class);

    @Inject
    private DeviceOnPartnerLocationRepository deviceOnPartnerLocationRepository;

    /**
     * POST  /deviceOnPartnerLocations -> Create a new deviceOnPartnerLocation.
     */
    @RequestMapping(value = "/deviceOnPartnerLocations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody DeviceOnPartnerLocation deviceOnPartnerLocation) {
        log.debug("REST request to save DeviceOnPartnerLocation : {}", deviceOnPartnerLocation);
        deviceOnPartnerLocationRepository.save(deviceOnPartnerLocation);
    }

    /**
     * GET  /deviceOnPartnerLocations -> get all the deviceOnPartnerLocations.
     */
    @RequestMapping(value = "/deviceOnPartnerLocations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeviceOnPartnerLocation> getAll() {
        log.debug("REST request to get all DeviceOnPartnerLocations");
        return deviceOnPartnerLocationRepository.findAll();
    }

    /**
     * GET  /deviceOnPartnerLocations/:id -> get the "id" deviceOnPartnerLocation.
     */
    @RequestMapping(value = "/deviceOnPartnerLocations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceOnPartnerLocation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get DeviceOnPartnerLocation : {}", id);
        DeviceOnPartnerLocation deviceOnPartnerLocation = deviceOnPartnerLocationRepository.findOne(id);
        if (deviceOnPartnerLocation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deviceOnPartnerLocation, HttpStatus.OK);
    }

    /**
     * DELETE  /deviceOnPartnerLocations/:id -> delete the "id" deviceOnPartnerLocation.
     */
    @RequestMapping(value = "/deviceOnPartnerLocations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete DeviceOnPartnerLocation : {}", id);
        deviceOnPartnerLocationRepository.delete(id);
    }
}
