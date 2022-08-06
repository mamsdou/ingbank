package fr.cogigroup.ingbank.services;


//import com.google.common.annotations.VisibleForTesting;
import fr.cogigroup.ingbank.exceptions.NoSuchAccountException;
import fr.cogigroup.ingbank.helpers.AppHelper;
import fr.cogigroup.ingbank.mapping.BankAccountDtoMapper;
import fr.cogigroup.ingbank.models.BankAccount;
import fr.cogigroup.ingbank.models.Operation;
import fr.cogigroup.ingbank.models.OperationType;
import fr.cogigroup.ingbank.models.dto.BankAccountDto;
import fr.cogigroup.ingbank.repositories.BankAccountRepository;
import fr.cogigroup.ingbank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class OperationService {


    @Autowired
    private  OperationRepository operationRepository;
    @Autowired
    private  BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountDtoMapper dtoMapper;

    private AppHelper appHelper;


    /**
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public ResponseEntity<?> doWithdrawal(long accountId, long amount) throws NoSuchAccountException {
        try{

            if (amount<0.01){
                return new ResponseEntity<>(null, HttpStatus.valueOf("Montant doit etre superieur à 0.01"));
            }
            BankAccount bankAccount = bankAccountRepository.findById(accountId).get();
            if (bankAccount==null){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            if (bankAccount.getSolde()<amount){
                return new ResponseEntity<>(appHelper.response(bankAccount,HttpStatus.NOT_FOUND.value(),false,
                        "Montant > Solde"),HttpStatus.NOT_FOUND);
            }
            Operation operation = createAndPerformOperation(accountId,amount, OperationType.WITHDRAWAL);
            bankAccount.getOperations().add(operation);
            return new ResponseEntity<>(dtoMapper.mapEntityToDto(bankAccount), HttpStatus.OK);

        }
        catch (Exception exception){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * deposit the specified amount into the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public ResponseEntity doDeposit(long accountId, long amount) throws NoSuchAccountException {
        Operation operation = createAndPerformOperation(accountId,amount,OperationType.DEPOSIT);
        BankAccount bankAccount = bankAccountRepository.findById(accountId).get();

        bankAccount.getOperations().add(operation);
        return new ResponseEntity<>(dtoMapper.mapEntityToDto(bankAccount), HttpStatus.OK);
    }


    /**
     * create and perform the specified operation on the given account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @param operationType the transaction type(debit or credit)
     * @return created operation
     * @throws NoSuchAccountException
     */
    //@VisibleForTesting
    Operation createAndPerformOperation(long accountId, long amount, OperationType operationType) throws NoSuchAccountException {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        try{
            if(!optionalBankAccount.isPresent()){
                return null;
            }
            BankAccount account = optionalBankAccount.get();
            int opType = operationType.equals(OperationType.WITHDRAWAL) ? -1 : 1;
            Operation operation = new Operation();
            operation.setMontant(opType*amount);
            operation.setDate(Instant.now());
            operation.setAccount(account);
            operation.setType(operationType);
            account.solde+=opType*amount;
            operationRepository.save(operation);
            return operation;
        }
        catch (Exception e ){
            throw new NoSuchAccountException("");
        }

    }
}
