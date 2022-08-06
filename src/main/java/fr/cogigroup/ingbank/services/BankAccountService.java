package fr.cogigroup.ingbank.services;


import fr.cogigroup.ingbank.exceptions.NoSuchAccountException;
import fr.cogigroup.ingbank.helpers.AppHelper;
import fr.cogigroup.ingbank.mapping.BankAccountDtoMapper;
import fr.cogigroup.ingbank.models.BankAccount;
import fr.cogigroup.ingbank.models.Operation;
import fr.cogigroup.ingbank.repositories.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountDtoMapper bankAccountDtoMapper;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    private  AppHelper appHelper;

    /**
     *
     * @param accountId account identifier
     * @return all operations on a given account
     * @throws NoSuchAccountException
     */
    public List<Operation> listAllOperations(long accountId) throws NoSuchAccountException {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+accountId);
        }
        return optionalBankAccount.get().operations;
    }

    /**
     *
     * @param accountId account identifier
     * @return  the account state including latest operations
     * @throws NoSuchAccountException
     */
    public ResponseEntity<?> showSoldeCustomer(long accountId) throws NoSuchAccountException {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+accountId);
        }
        return bankAccountDtoMapper.mapEntityToDto(optionalBankAccount.get());
    }

    @Transactional
    public ResponseEntity<?> saveAccount(BankAccount bankAccount){

        BankAccount bankAccount1 = new BankAccount(0L, bankAccount.getUserFirstName(),
                bankAccount.getUserLastName(), bankAccount.getAddress(), bankAccount.getPhoneNumber());
        return new ResponseEntity<>(bankAccountRepository.save(bankAccount1), HttpStatus.OK);
    }
}
