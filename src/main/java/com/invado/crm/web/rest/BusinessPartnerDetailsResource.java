package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.BusinessPartnerDetails;
import com.invado.crm.repository.BusinessPartnerDetailsRepository;
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
 * REST controller for managing BusinessPartnerDetails.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerDetailsResource.class);

    @Inject
    private BusinessPartnerDetailsRepository businessPartnerDetailsRepository;

    /**
     * POST  /businessPartnerDetailss -> Create a new businessPartnerDetails.
     */
    @RequestMapping(value = "/businessPartnerDetailss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody BusinessPartnerDetails businessPartnerDetails) {
        log.debug("REST request to save BusinessPartnerDetails : {}", businessPartnerDetails);
        businessPartnerDetailsRepository.save(businessPartnerDetails);
    }

    /**
     * GET  /businessPartnerDetailss -> get all the businessPartnerDetailss.
     */
    @RequestMapping(value = "/businessPartnerDetailss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusinessPartnerDetails> getAll() {
        log.debug("REST request to get all BusinessPartnerDetailss");
        return businessPartnerDetailsRepository.findAll();
    }

    /**
     * GET  /businessPartnerDetailss/:id -> get the "id" businessPartnerDetails.
     */
    @RequestMapping(value = "/businessPartnerDetailss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusinessPartnerDetails> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BusinessPartnerDetails : {}", id);
        BusinessPartnerDetails businessPartnerDetails = businessPartnerDetailsRepository.findOne(id);
        if (businessPartnerDetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessPartnerDetails, HttpStatus.OK);
    }

    /**
     * DELETE  /businessPartnerDetailss/:id -> delete the "id" businessPartnerDetails.
     */
    @RequestMapping(value = "/businessPartnerDetailss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartnerDetails : {}", id);
        businessPartnerDetailsRepository.delete(id);
    }
}
