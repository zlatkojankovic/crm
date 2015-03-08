package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.BusinessPartnerDetailsType;
import com.invado.crm.repository.BusinessPartnerDetailsTypeRepository;
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
 * REST controller for managing BusinessPartnerDetailsType.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerDetailsTypeResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerDetailsTypeResource.class);

    @Inject
    private BusinessPartnerDetailsTypeRepository businessPartnerDetailsTypeRepository;

    /**
     * POST  /businessPartnerDetailsTypes -> Create a new businessPartnerDetailsType.
     */
    @RequestMapping(value = "/businessPartnerDetailsTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody BusinessPartnerDetailsType businessPartnerDetailsType) {
        log.debug("REST request to save BusinessPartnerDetailsType : {}", businessPartnerDetailsType);
        businessPartnerDetailsTypeRepository.save(businessPartnerDetailsType);
    }

    /**
     * GET  /businessPartnerDetailsTypes -> get all the businessPartnerDetailsTypes.
     */
    @RequestMapping(value = "/businessPartnerDetailsTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusinessPartnerDetailsType> getAll() {
        log.debug("REST request to get all BusinessPartnerDetailsTypes");
        return businessPartnerDetailsTypeRepository.findAll();
    }

    /**
     * GET  /businessPartnerDetailsTypes/:id -> get the "id" businessPartnerDetailsType.
     */
    @RequestMapping(value = "/businessPartnerDetailsTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusinessPartnerDetailsType> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BusinessPartnerDetailsType : {}", id);
        BusinessPartnerDetailsType businessPartnerDetailsType = businessPartnerDetailsTypeRepository.findOne(id);
        if (businessPartnerDetailsType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessPartnerDetailsType, HttpStatus.OK);
    }

    /**
     * DELETE  /businessPartnerDetailsTypes/:id -> delete the "id" businessPartnerDetailsType.
     */
    @RequestMapping(value = "/businessPartnerDetailsTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartnerDetailsType : {}", id);
        businessPartnerDetailsTypeRepository.delete(id);
    }
}
