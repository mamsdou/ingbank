package fr.cogigroup.ingbank.controllers;

import fr.cogigroup.ingbank.handlers.ErrorHandler;
import fr.cogigroup.ingbank.models.BankAccount;
import fr.cogigroup.ingbank.models.Operation;
import fr.cogigroup.ingbank.models.OperationType;
import fr.cogigroup.ingbank.models.dto.OperationTreatment;
import fr.cogigroup.ingbank.repositories.BankAccountRepository;
import fr.cogigroup.ingbank.repositories.OperationRepository;
import fr.cogigroup.ingbank.services.BankAccountService;
import fr.cogigroup.ingbank.services.OperationService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static fr.cogigroup.ingbank.helper.TestHelper.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BankAccountConntrollerTest {


    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private ErrorHandler globalErrorHandler;

    private MockMvc restMvc;


    @Before("")
    public void setUp() {

        BankAccountConntroller bankAccountResources = new BankAccountConntroller(bankAccountService, operationService);
        this.restMvc = MockMvcBuilders.standaloneSetup(bankAccountResources).setControllerAdvice(globalErrorHandler)
                .build();

    }
    @Test
    @Transactional
    void printAccountState() throws Exception {
        BankAccount account = new BankAccount();
        account.setSolde(1000L);
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        restMvc.perform(get("/api/accounts/{id}", account.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.latestOperations").isEmpty())
                .andExpect(jsonPath("$.solde").value(account.getSolde()));
    }

    @Test
    void showOperationsList() throws Exception {
        BankAccount account = new BankAccount();
        account.setSolde(0L);
        account.setUserFirstName("Ndiaye");
        account.setUserLastName("Ma");
        account.setAddress("Montreuil");
        account.setAddress("Montreuil");
        account.setPhoneNumber("0723456785");
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setType(OperationType.WITHDRAWAL);
        operation.setMontant(25000L);
        operationRepository.saveAndFlush(operation);
        Operation operation2 = new Operation();
        operation2.setAccount(account);
        operation2.setType(OperationType.DEPOSIT);
        operation2.setMontant(25000L);
        operationRepository.saveAndFlush(operation2);
        account.getOperations().add(operation);
        account.getOperations().add(operation2);
        restMvc.perform(get("/api/accounts/{id}/history", account.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*]").isNotEmpty())
                .andExpect(jsonPath("$.[*].id").value(hasItems(operation.getId().intValue(),operation2.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItems(operation.getMontant().intValue(),operation2.getMontant().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItems(operation.getType().toString(),operation2.getType().toString())));
    }

    @Test
    @Transactional
    void saveAccount() {
    }

    @Test
    @Transactional
    void deposit() throws Exception {
        BankAccount account = new BankAccount();
        account.setSolde(0L);
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        restMvc.perform(put("/api/accounts/{accountId}/deposit", account.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(new OperationTreatment(15000))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latestOperations").isNotEmpty())
                .andExpect(jsonPath("$.solde").value(15000));
    }

    @Test
    public void deposit_should_return_error_message_and_404_code_status() throws Exception {

        restMvc.perform(put("/api/accounts/555555/deposit")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(new OperationTreatment(2522L))))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @Transactional
    void withdrawal()  throws Exception{
        BankAccount account = new BankAccount();
        account.setSolde(0L);
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        restMvc.perform(put("/api/accounts/{accountId}/withdrawal", account.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(new OperationTreatment(100))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latestOperations").isNotEmpty())
                .andExpect(jsonPath("$.solde").value(-100));
    }

    @Test
    public void withdrawal_should_return_error_message_and_404_code_status() throws Exception {

        restMvc.perform(put("/api/accounts/{accountId}/withdrawal", 575556L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(new OperationTreatment(8200L))))
                .andExpect(status().is4xxClientError());

    }
}