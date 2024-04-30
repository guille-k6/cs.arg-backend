package backend.repository;

import backend.models.MoneyPetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyPetitionRepository extends JpaRepository<MoneyPetition, Long> {

}
