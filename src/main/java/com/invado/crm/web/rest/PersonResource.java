package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.Person;
import com.invado.crm.repository.PersonRepository;
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
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Inject
    private PersonRepository personRepository;

    /**
     * POST  /persons -> Create a new person.
     */
    @RequestMapping(value = "/persons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Person person) {
        log.debug("REST request to save Person : {}", person);
        personRepository.save(person);
    }

    /**
     * GET  /persons -> get all the persons.
     */
    @RequestMapping(value = "/persons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Person> getAll() {
        log.debug("REST request to get all Persons");
        return personRepository.findAll();
    }

    /**
     * GET  /persons/:id -> get the "id" person.
     */
    @RequestMapping(value = "/persons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /**
     * DELETE  /persons/:id -> delete the "id" person.
     */
    @RequestMapping(value = "/persons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
    }
}
