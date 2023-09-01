package com.guille.security.repository;

import com.guille.security.models.TradePetition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePetitionRepository extends JpaRepository<TradePetition, Long> {
    @Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getAllTradePetitions(Pageable pageable);

    /*@Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getTradePetitionsFiltered(
        @Param("search_type") PetitionType search_type,
        @Param("item_type") String item_type,
        @Param("name") String name,
        @Param("weapon") String weapon,
        @Param("rarity") String rarity,
        Pageable pageable
    );*/

    /*@Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getTradePetitionsWithoutType();*/

    /*@Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getTradePetitionsWeapon();*/

    @Query(value = "SELECT DISTINCT tp.id as tp_id, tp.* FROM trade_petition tp " +
            "INNER JOIN requested_crate ON tp.id = requested_crate.trade_id " +
            "INNER JOIN crate ON requested_crate.crate_id = crate.id " +
            "WHERE  (:petitionType IS NULL OR requested_crate.trade_type = :petitionType) " +
            "AND (:name IS NULL OR LOWER(crate.name) LIKE '%' || :name || '%')"
            , nativeQuery = true)
    Page<TradePetition> getTradePetitionsCrate(Boolean petitionType, String name, Pageable pageable);

    /*@Query( "SELECT tp FROM TradePetition tp " )
    Page<TradePetition> getTradePetitionsSticker();*/
}
