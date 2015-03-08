package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.BusinessPartnerAddress;
import com.invado.crm.repository.BusinessPartnerAddressRepository;
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
 * REST controller for managing BusinessPartnerAddress.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerAddressResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerAddressResource.class);

    @Inject
    private BusinessPartnerAddressRepository businessPartnerAddressRepository;

    /**
     * POST  /businessPartnerAddresss -> Create a new businessPartnerAddress.
     */
    @RequestMapping(value = "/businessPartnerAddresss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody BusinessPartnerAddress businessPartnerAddress) {
        log.debug("REST request to save BusinessPartnerAddress : {}", businessPartnerAddress);
        businessPartnerAddressRepository.save(businessPartnerAddress);
    }

    /**
     * GET  /businessPartnerAddresss -> get all the businessPartnerAddresss.
     */
    @RequestMapping(value = "/businessPartnerAddresss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusinessPartnerAddress> getAll() {
        log.debug("REST request to get all BusinessPartnerAddresss");
        return businessPartnerAddressRepository.findAll();
    }

    /**
     * GET  /businessPartnerAddresss/:id -> get the "id" businessPartnerAddress.
     */
    @RequestMapping(value = "/businessPartnerAddresss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusinessPartnerAddress> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BusinessPartnerAddress : {}", id);
        BusinessPartnerAddress businessPartnerAddress = businessPartnerAddressRepository.findOne(id);
        if (businessPartnerAddress == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessPartnerAddress, HttpStatus.OK);
    }

    /**
     * DELETE  /businessPartnerAddresss/:id -> delete the "id" businessPartnerAddress.
     */
    @RequestMapping(value = "/businessPartnerAddresss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartnerAddress : {}", id);
        businessPartnerAddressRepository.delete(id);
    }
}
