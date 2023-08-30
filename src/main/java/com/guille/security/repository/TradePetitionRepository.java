package com.guille.security.repository;

import com.guille.security.models.TradePetition;
import com.guille.security.models.enums.PetitionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePetitionRepository extends JpaRepository<TradePetition, Long> {
    @Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getAllTradePetitions(Pageable pageable);

    @Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getTradePetitionsFiltered(
        @Param("search_type") PetitionType search_type,
        @Param("item_type") String item_type,
        @Param("name") String name,
        @Param("weapon") String weapon,
        @Param("rarity") String rarity,
        Pageable pageable
    );
}
