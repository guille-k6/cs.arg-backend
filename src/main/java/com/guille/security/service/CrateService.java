package com.guille.security.service;

import com.guille.security.models.Crate;
import com.guille.security.models.Skin;
import com.guille.security.repository.CrateRepository;
import com.guille.security.repository.SkinRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrateService {
    private final CrateRepository crateRepository;

    @Autowired
    public CrateService(CrateRepository crateRepository){
        this.crateRepository = crateRepository;
    }

    @Transactional
    public void saveAll(List<Crate> crates){
        this.crateRepository.saveAll(crates);
    }

    public List<Crate> findAll(){
        return this.crateRepository.findAll();
    }
}
