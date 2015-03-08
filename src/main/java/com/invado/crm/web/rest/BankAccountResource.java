package com.invado.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.invado.crm.domain.BankAccount;
import com.invado.crm.repository.BankAccountRepository;
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
 * REST controller for managing BankAccount.
 */
@RestController
@RequestMapping("/api")
public class BankAccountResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountResource.class);

    @Inject
    private BankAccountRepository bankAccountRepository;

    /**
     * POST  /bankAccounts -> Create a new bankAccount.
     */
    @RequestMapping(value = "/bankAccounts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody BankAccount bankAccount) {
        log.debug("REST request to save BankAccount : {}", bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    /**
     * GET  /bankAccounts -> get all the bankAccounts.
     */
    @RequestMapping(value = "/bankAccounts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BankAccount> getAll() {
        log.debug("REST request to get all BankAccounts");
        return bankAccountRepository.findAll();
    }

    /**
     * GET  /bankAccounts/:id -> get the "id" bankAccount.
     */
    @RequestMapping(value = "/bankAccounts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankAccount> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BankAccount : {}", id);
        BankAccount bankAccount = bankAccountRepository.findOne(id);
        if (bankAccount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bankAccount, HttpStatus.OK);
    }

    /**
     * DELETE  /bankAccounts/:id -> delete the "id" bankAccount.
     */
    @RequestMapping(value = "/bankAccounts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BankAccount : {}", id);
        bankAccountRepository.delete(id);
    }
}
