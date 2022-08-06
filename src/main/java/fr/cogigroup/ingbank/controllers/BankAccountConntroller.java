package fr.cogigroup.ingbank.controllers;


import fr.cogigroup.ingbank.exceptions.NoSuchAccountException;
import fr.cogigroup.ingbank.models.BankAccount;
import fr.cogigroup.ingbank.models.Operation;
import fr.cogigroup.ingbank.models.dto.BankAccountDto;
import fr.cogigroup.ingbank.models.dto.OperationTreatment;
import fr.cogigroup.ingbank.services.BankAccountService;
import fr.cogigroup.ingbank.services.OperationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/accounts/")
public class BankAccountConntroller {



    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    public BankAccountConntroller(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "return given account state and recent operations")
    @ApiResponses(value = {@ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "200", description = "Ok")})
    @GetMapping("{accountId}")
    public ResponseEntity printAccountState(@PathVariable long accountId) throws NoSuchAccountException {
        return bankAccountService.showSoldeCustomer(accountId);
    }




    @io.swagger.v3.oas.annotations.Operation(summary = "lists all given account operations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Bad request"),
            @ApiResponse(responseCode = "400", description = "Not found"),
    })
    @GetMapping("{accountId}/history")
    public List<Operation> showOperationsList(@PathVariable long accountId) throws NoSuchAccountException {
        return bankAccountService.listAllOperations(accountId);
    }


    @PostMapping(value = "/newAccount")
    public ResponseEntity saveAccount(@RequestBody BankAccount bankAccount){
        return bankAccountService.saveAccount(bankAccount);
    }

    @PutMapping(value = "{accountId}/deposit")
    @Transactional
    public ResponseEntity deposit(@PathVariable long accountId,
                                  @RequestBody OperationTreatment operationCommand) throws NoSuchAccountException {
        return operationService.doDeposit(accountId,operationCommand.getAmount());
    }



    @PutMapping(value = "{accountId}/withdrawal")
    @Transactional
    public ResponseEntity withdrawal(@PathVariable long accountId,
                                 @RequestBody OperationTreatment operationCommand) throws NoSuchAccountException {
        return operationService.doWithdrawal(accountId,operationCommand.getAmount());
    }
}
