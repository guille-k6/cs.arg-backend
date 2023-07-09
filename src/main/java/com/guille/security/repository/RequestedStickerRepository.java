package com.guille.security.repository;

import com.guille.security.models.RequestedSticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedStickerRepository extends JpaRepository<RequestedSticker, Long> {
}
