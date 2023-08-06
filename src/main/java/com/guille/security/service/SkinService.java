package com.guille.security.service;

import com.guille.security.models.Skin;
import com.guille.security.repository.SkinRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public List<Skin> findSkinsFiltered(HashMap<String, String> parameters){
        String name = null;
        String weapon = null;
        String category = null;
        String pattern = null;
        String rarity = null;

        if (parameters.size() == 0){
            return this.skinRepository.findAll();
        }

        // If the parameter exists -> Underscores to spaces -> Assign to the variable.
        if(parameters.containsKey("name")) name = parameters.get("name").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("weapon")) weapon = parameters.get("weapon").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("category")) category = parameters.get("category").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("pattern")) pattern = parameters.get("pattern").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("rarity")) rarity = parameters.get("rarity").replaceAll("_"," ").toLowerCase();

        return this.skinRepository.getSkinsFiltered(name, weapon, category, pattern, rarity);

    }
}
