package fr.cogigroup.ingbank.repositories;

import fr.cogigroup.ingbank.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
