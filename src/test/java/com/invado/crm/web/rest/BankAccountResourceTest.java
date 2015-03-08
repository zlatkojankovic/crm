package com.invado.crm.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.invado.crm.Application;
import com.invado.crm.domain.BankAccount;
import com.invado.crm.repository.BankAccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BankAccountResource REST controller.
 *
 * @see BankAccountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BankAccountResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_ACCOUNT_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_ACCOUNT_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_FROM = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_FROM = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_FROM_STR = dateTimeFormatter.print(DEFAULT_DATE_FROM);

    private static final DateTime DEFAULT_DATE_TO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_TO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_TO_STR = dateTimeFormatter.print(DEFAULT_DATE_TO);
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";

    @Inject
    private BankAccountRepository bankAccountRepository;

    private MockMvc restBankAccountMockMvc;

    private BankAccount bankAccount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankAccountResource bankAccountResource = new BankAccountResource();
        ReflectionTestUtils.setField(bankAccountResource, "bankAccountRepository", bankAccountRepository);
        this.restBankAccountMockMvc = MockMvcBuilders.standaloneSetup(bankAccountResource).build();
    }

    @Before
    public void initTest() {
        bankAccount = new BankAccount();
        bankAccount.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
        bankAccount.setStatus(DEFAULT_STATUS);
        bankAccount.setDateFrom(DEFAULT_DATE_FROM);
        bankAccount.setDateTo(DEFAULT_DATE_TO);
        bankAccount.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createBankAccount() throws Exception {
        // Validate the database is empty
        assertThat(bankAccountRepository.findAll()).hasSize(0);

        // Create the BankAccount
        restBankAccountMockMvc.perform(post("/api/bankAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
                .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        assertThat(bankAccounts).hasSize(1);
        BankAccount testBankAccount = bankAccounts.iterator().next();
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBankAccount.getDateFrom().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testBankAccount.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testBankAccount.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get all the bankAccounts
        restBankAccountMockMvc.perform(get("/api/bankAccounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(bankAccount.getId().intValue()))
                .andExpect(jsonPath("$.[0].accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].dateFrom").value(DEFAULT_DATE_FROM_STR))
                .andExpect(jsonPath("$.[0].dateTo").value(DEFAULT_DATE_TO_STR))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bankAccounts/{id}", bankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bankAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM_STR))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO_STR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankAccount() throws Exception {
        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bankAccounts/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Update the bankAccount
        bankAccount.setAccountNumber(UPDATED_ACCOUNT_NUMBER);
        bankAccount.setStatus(UPDATED_STATUS);
        bankAccount.setDateFrom(UPDATED_DATE_FROM);
        bankAccount.setDateTo(UPDATED_DATE_TO);
        bankAccount.setType(UPDATED_TYPE);
        restBankAccountMockMvc.perform(post("/api/bankAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
                .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        assertThat(bankAccounts).hasSize(1);
        BankAccount testBankAccount = bankAccounts.iterator().next();
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBankAccount.getDateFrom().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testBankAccount.getDateTo().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_TO);
        assertThat(testBankAccount.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc.perform(delete("/api/bankAccounts/{id}", bankAccount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        assertThat(bankAccounts).hasSize(0);
    }
}
