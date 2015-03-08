package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.PhoneNumber;
import com.invado.crm.repository.PhoneNumberRepository;
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
 * REST controller for managing PhoneNumber.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberResource.class);

    @Inject
    private PhoneNumberRepository phoneNumberRepository;

    /**
     * POST  /phoneNumbers -> Create a new phoneNumber.
     */
    @RequestMapping(value = "/phoneNumbers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody PhoneNumber phoneNumber) {
        log.debug("REST request to save PhoneNumber : {}", phoneNumber);
        phoneNumberRepository.save(phoneNumber);
    }

    /**
     * GET  /phoneNumbers -> get all the phoneNumbers.
     */
    @RequestMapping(value = "/phoneNumbers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PhoneNumber> getAll() {
        log.debug("REST request to get all PhoneNumbers");
        return phoneNumberRepository.findAll();
    }

    /**
     * GET  /phoneNumbers/:id -> get the "id" phoneNumber.
     */
    @RequestMapping(value = "/phoneNumbers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhoneNumber> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PhoneNumber : {}", id);
        PhoneNumber phoneNumber = phoneNumberRepository.findOne(id);
        if (phoneNumber == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(phoneNumber, HttpStatus.OK);
    }

    /**
     * DELETE  /phoneNumbers/:id -> delete the "id" phoneNumber.
     */
    @RequestMapping(value = "/phoneNumbers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumber : {}", id);
        phoneNumberRepository.delete(id);
    }
}
