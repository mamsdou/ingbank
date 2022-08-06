package fr.cogigroup.ingbank.services;

import fr.cogigroup.ingbank.exceptions.NoSuchAccountException;
import fr.cogigroup.ingbank.mapping.BankAccountDtoMapper;
import fr.cogigroup.ingbank.models.BankAccount;
import fr.cogigroup.ingbank.models.Operation;
import fr.cogigroup.ingbank.models.OperationType;
import fr.cogigroup.ingbank.repositories.BankAccountRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class BankAccountServiceTest {



    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountDtoMapper accountDtoMapper;

    @InjectMocks
    private BankAccountService bankAccountService;

    private List<Operation> operations;
    private BankAccount account ;

    @Before("")
    public void setUp(){
        account = new BankAccount();
        account.setSolde(5000L);
        account.setId(12L);
        operations = new ArrayList<>();
        operations.add(new Operation(Instant.now(), OperationType.DEPOSIT,10000L,account));
        account.setOperations(operations);
    }

    @Test
    public void listAllOperations_should_throw_exception_for_no_such_account() throws Exception, NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
        bankAccountService.listAllOperations(12L);
        Assert.fail("should have thrown NoSuchAccountException ");
    }


    @Test
    public void listAllOperations_should_successfully_return_all_account_operations() throws NoSuchAccountException {
        when(bankAccountRepository.findById(12L)).thenReturn(Optional.of(account));
        when(accountDtoMapper.mapEntityToDto(any(BankAccount.class))).thenCallRealMethod();
        List<Operation> operations = bankAccountService.listAllOperations(12L);
        assertThat(operations).isNotEmpty();
        assertThat(operations).hasSize(1);
    }

}