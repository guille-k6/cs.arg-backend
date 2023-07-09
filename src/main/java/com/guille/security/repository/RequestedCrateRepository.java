package com.guille.security.repository;

import com.guille.security.models.RequestedCrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedCrateRepository extends JpaRepository<RequestedCrate, Long> {
}
