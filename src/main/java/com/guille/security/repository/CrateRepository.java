package com.guille.security.repository;

import com.guille.security.models.Crate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrateRepository extends JpaRepository<Crate, String> {
}
