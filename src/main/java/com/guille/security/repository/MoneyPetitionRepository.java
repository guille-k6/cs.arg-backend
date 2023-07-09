package com.guille.security.repository;

import com.guille.security.models.MoneyPetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyPetitionRepository extends JpaRepository<MoneyPetition, Long> {

}
