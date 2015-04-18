package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.BusinessPartnerContactDetails;
import com.invado.crm.repository.BusinessPartnerContactDetailsRepository;
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
 * REST controller for managing BusinessPartnerContactDetails.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerContactDetailsResource.class);

    @Inject
    private BusinessPartnerContactDetailsRepository businessPartnerContactDetailsRepository;

    /**
     * POST  /businessPartnerContactDetailss -> Create a new businessPartnerContactDetails.
     */
    @RequestMapping(value = "/businessPartnerContactDetailss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody BusinessPartnerContactDetails businessPartnerContactDetails) {
        log.debug("REST request to save BusinessPartnerContactDetails : {}", businessPartnerContactDetails);
        businessPartnerContactDetailsRepository.save(businessPartnerContactDetails);
    }

    /**
     * GET  /businessPartnerContactDetailss -> get all the businessPartnerContactDetailss.
     */
    @RequestMapping(value = "/businessPartnerContactDetailss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusinessPartnerContactDetails> getAll() {
        log.debug("REST request to get all BusinessPartnerContactDetailss");
        return businessPartnerContactDetailsRepository.findAll();
    }

    /**
     * GET  /businessPartnerContactDetailss/:id -> get the "id" businessPartnerContactDetails.
     */
    @RequestMapping(value = "/businessPartnerContactDetailss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusinessPartnerContactDetails> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BusinessPartnerContactDetails : {}", id);
        BusinessPartnerContactDetails businessPartnerContactDetails = businessPartnerContactDetailsRepository.findOne(id);
        if (businessPartnerContactDetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessPartnerContactDetails, HttpStatus.OK);
    }

    /**
     * GET  /businessPartnersContactDetailssPerPartner/:partnerId -> get the "partnerId" businessPartnerContactDetails.
     */
    @RequestMapping(value = "/businessPartnersContactDetailssPerPartner{partnerId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusinessPartnerContactDetails> getContactsPerBusinessPartner(@PathVariable Long partnerId, HttpServletResponse response) {
        System.out.println("ovo je izgleda ipal proonadjeno");
        log.debug("REST request to get BusinessPartnerContactDetailsPerPartner : {}", partnerId);
        List<BusinessPartnerContactDetails> businessPartnerContactDetails = businessPartnerContactDetailsRepository.findAllPerPartnerId(partnerId);
        return businessPartnerContactDetails;
    }

    /**
     * DELETE  /businessPartnerContactDetailss/:id -> delete the "id" businessPartnerContactDetails.
     */
    @RequestMapping(value = "/businessPartnerContactDetailss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartnerContactDetails : {}", id);
        businessPartnerContactDetailsRepository.delete(id);
    }
}
