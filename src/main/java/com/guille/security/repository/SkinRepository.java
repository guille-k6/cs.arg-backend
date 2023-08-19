package com.guille.security.repository;

import com.guille.security.models.Skin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkinRepository extends JpaRepository<Skin, String> {

    // My parameters came in lowercase from the service layer.
    @Query( "SELECT s FROM Skin s " +
            "WHERE (:name IS NULL OR lower(s.name) LIKE %:name%)" +
            "AND (:weapon IS NULL OR lower(s.weapon) = :weapon)" +
            "AND (:category IS NULL OR lower(s.category) = :category)" +
            "AND (:pattern IS NULL OR lower(s.pattern) = :pattern)" +
            "AND (:rarity IS NULL OR lower(s.rarity) = :rarity)"
    )
    Page<Skin> getSkinsFiltered(
            @Param("name") String name,
            @Param("weapon") String weapon,
            @Param("category") String category,
            @Param("pattern") String pattern,
            @Param("rarity") String rarity,
            Pageable pageable
            );

}