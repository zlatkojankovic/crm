package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.BusinessPartner;
import com.invado.crm.repository.BusinessPartnerRepository;
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
 * REST controller for managing BusinessPartner.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerResource.class);

    @Inject
    private BusinessPartnerRepository businessPartnerRepository;

    /**
     * POST  /businessPartners -> Create a new businessPartner.
     */
    @RequestMapping(value = "/businessPartners",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody BusinessPartner businessPartner) {
        log.debug("REST request to save BusinessPartner : {}", businessPartner);
        businessPartnerRepository.save(businessPartner);
    }

    /**
     * GET  /businessPartners -> get all the businessPartners.
     */
    @RequestMapping(value = "/businessPartners",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusinessPartner> getAll() {
        log.debug("REST request to get all BusinessPartners");
        return businessPartnerRepository.findAll();
    }

    /**
     * GET  /businessPartners/:id -> get the "id" businessPartner.
     */
    @RequestMapping(value = "/businessPartners/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusinessPartner> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BusinessPartner : {}", id);
        BusinessPartner businessPartner = businessPartnerRepository.findOne(id);
        if (businessPartner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessPartner, HttpStatus.OK);
    }

    /**
     * DELETE  /businessPartners/:id -> delete the "id" businessPartner.
     */
    @RequestMapping(value = "/businessPartners/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartner : {}", id);
        businessPartnerRepository.delete(id);
    }
}
