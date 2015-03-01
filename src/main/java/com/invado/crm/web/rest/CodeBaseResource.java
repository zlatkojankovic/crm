package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.CodeBase;
import com.invado.crm.repository.CodeBaseRepository;
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
 * REST controller for managing CodeBase.
 */
@RestController
@RequestMapping("/api")
public class CodeBaseResource {

    private final Logger log = LoggerFactory.getLogger(CodeBaseResource.class);

    @Inject
    private CodeBaseRepository codeBaseRepository;

    /**
     * POST  /codeBases -> Create a new codeBase.
     */
    @RequestMapping(value = "/codeBases",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CodeBase codeBase) {
        log.debug("REST request to save CodeBase : {}", codeBase);
        codeBaseRepository.save(codeBase);
    }

    /**
     * GET  /codeBases -> get all the codeBases.
     */
    @RequestMapping(value = "/codeBases",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CodeBase> getAll() {
        log.debug("REST request to get all CodeBases");
        return codeBaseRepository.findAll();
    }

    /**
     * GET  /codeBases/:id -> get the "id" codeBase.
     */
    @RequestMapping(value = "/codeBases/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CodeBase> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CodeBase : {}", id);
        CodeBase codeBase = codeBaseRepository.findOne(id);
        if (codeBase == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(codeBase, HttpStatus.OK);
    }

    /**
     * DELETE  /codeBases/:id -> delete the "id" codeBase.
     */
    @RequestMapping(value = "/codeBases/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CodeBase : {}", id);
        codeBaseRepository.delete(id);
    }
}
