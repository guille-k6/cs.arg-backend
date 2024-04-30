package backend.repository;

import backend.models.Sticker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, String> {
    @Query( "SELECT s FROM Sticker s " +
            "WHERE (:name IS NULL OR lower(s.name) LIKE %:name%)" +
            "AND (:rarity IS NULL OR lower(s.rarity) = :rarity)"
    )
    Page<Sticker> getStickersFiltered(@Param("name") String name,
                                      @Param("rarity") String rarity,
                                      Pageable pageable);
}
