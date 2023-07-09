package com.guille.security.service;

import com.guille.security.models.Skin;
import com.guille.security.models.Sticker;
import com.guille.security.repository.StickerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StickerService {

    private final StickerRepository stickerRepository;

    @Autowired
    public StickerService(StickerRepository stickerRepository){
        this.stickerRepository = stickerRepository;
    }

    @Transactional
    public void saveAll(List<Sticker> stickers){
        this.stickerRepository.saveAll(stickers);
    }

    public List<Sticker> findAll(){
        return this.stickerRepository.findAll();
    }

}
