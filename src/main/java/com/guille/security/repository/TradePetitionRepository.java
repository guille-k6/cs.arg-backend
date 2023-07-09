package com.guille.security.repository;

import com.guille.security.models.RequestedSticker;
import com.guille.security.models.TradePetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePetitionRepository extends JpaRepository<TradePetition, Long> {
}
