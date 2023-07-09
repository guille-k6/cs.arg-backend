package com.guille.security.service;

import com.guille.security.models.Skin;
import com.guille.security.repository.SkinRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkinService {
    private final SkinRepository skinRepository;

    @Autowired
    public SkinService(SkinRepository skinRepository){
        this.skinRepository = skinRepository;
    }

    @Transactional
    public void saveAll(List<Skin> skins){
        this.skinRepository.saveAll(skins);
    }

    public List<Skin> findAll(){
        return this.skinRepository.findAll();
    }
}
