package backend.repository;

import backend.models.TradePetition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePetitionRepository extends JpaRepository<TradePetition, Long> {
    @Query( value = "SELECT * FROM trade_petition tp ", nativeQuery = true )
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

    @Query(value = "SELECT DISTINCT tp.id as tp_id, tp.* FROM trade_petition tp " +
            "INNER JOIN requested_skin ON tp.id = requested_skin.trade_id " +
            "INNER JOIN skin ON requested_skin.skin_id = skin.id " +
            "WHERE  (:petitionType IS NULL OR requested_skin.trade_type = :petitionType) " +
            "AND (:name IS NULL OR LOWER(skin.name) LIKE '%' || :name || '%') " +
            "AND (:weapon IS NULL OR skin.weapon = :weapon) " +
            "AND (:rarity IS NULL OR skin.rarity = :rarity) " +
            "AND (:condition IS NULL OR requested_skin.condition = :condition) " +
            "AND (:pattern = -1 OR requested_skin.paint_pattern = :pattern) " +
            "AND (:stattrak IS NULL OR requested_skin.stattrak = :stattrak) " +
            "AND (:souvenir IS NULL OR requested_skin.souvenir = :souvenir) " +
            "AND (:special = false OR skin.category = 'Knives' OR skin.category = 'Gloves')"
            , nativeQuery = true)
    Page<TradePetition> getTradePetitionsWeapon(Boolean petitionType,
                                                String name,
                                                String weapon,
                                                String rarity,
                                                String condition,
                                                int pattern,
                                                Boolean stattrak,
                                                Boolean souvenir,
                                                Boolean special,
                                                Pageable pageable);

    @Query(value = "SELECT DISTINCT tp.id as tp_id, tp.* FROM trade_petition tp " +
            "INNER JOIN requested_crate ON tp.id = requested_crate.trade_id " +
            "INNER JOIN crate ON requested_crate.crate_id = crate.id " +
            "WHERE  (:petitionType IS NULL OR requested_crate.trade_type = :petitionType) " +
            "AND (:name IS NULL OR LOWER(crate.name) LIKE '%' || :name || '%')"
            , nativeQuery = true)
    Page<TradePetition> getTradePetitionsCrate(Boolean petitionType, String name, Pageable pageable);

    @Query(value = "SELECT DISTINCT tp.id as tp_id, tp.* FROM trade_petition tp " +
            "INNER JOIN requested_sticker ON tp.id = requested_sticker.trade_id " +
            "INNER JOIN sticker ON requested_sticker.sticker_id = sticker.id " +
            "WHERE  (:petitionType IS NULL OR requested_sticker.trade_type = :petitionType) " +
            "AND (:name IS NULL OR LOWER(sticker.name) LIKE '%' || :name || '%')"
            , nativeQuery = true)
    Page<TradePetition> getTradePetitionsSticker(Boolean petitionType, String name, Pageable pageable);
}
