package fr.cogigroup.ingbank.mapping;

import fr.cogigroup.ingbank.models.BankAccount;
import fr.cogigroup.ingbank.models.Operation;
import fr.cogigroup.ingbank.models.dto.BankAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountDtoMapper {

    public ResponseEntity<?> mapEntityToDto(BankAccount account){
        BankAccountDto dto = new BankAccountDto();
        dto.setSolde(account.getSolde());
        List<Operation> recentOperations = account.getOperations()
                .stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed())
                .limit(10).collect(Collectors.toList());
        dto.setLatestOperations(recentOperations);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
