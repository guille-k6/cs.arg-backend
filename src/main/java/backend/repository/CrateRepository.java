package backend.repository;

import backend.models.Crate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrateRepository extends JpaRepository<Crate, String> {
    @Query( "SELECT c FROM Crate c " +
            "WHERE (:name IS NULL OR lower(c.name) LIKE %:name%)"
    )
    Page<Crate> getCratesFiltered(@Param("name") String name, Pageable pageable);
}
