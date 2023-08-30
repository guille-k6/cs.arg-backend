package com.guille.security.service;

import com.guille.security.models.Skin;
import com.guille.security.repository.SkinRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.guille.security.utils.UtilMethods.tryParseInt;

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

    public Page<Skin> findSkinsFiltered(HashMap<String, String> parameters){
        String name = null;
        String weapon = null;
        String category = null;
        String pattern = null;
        String rarity = null;
        String page = null;
        String sortAttribute = null;
        String direction = null;

        final Pageable pageable;
        final int MAX_SKINS_PER_PAGE = 20;

        if (parameters.size() == 0){
            pageable = PageRequest.of(0, MAX_SKINS_PER_PAGE);
            return this.skinRepository.findAll(pageable);
        }

        // If the parameter exists -> Underscores to spaces -> Assign to the variable.
        if(parameters.containsKey("name")) name = parameters.get("name").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("weapon")) weapon = parameters.get("weapon").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("category")) category = parameters.get("category").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("pattern")) pattern = parameters.get("pattern").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("rarity")) rarity = parameters.get("rarity").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("page")) page = parameters.get("page");
        if(parameters.containsKey("sortAttribute")) sortAttribute = parameters.get("sortAttribute").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("direction")) direction = parameters.get("direction").replaceAll("_"," ").toLowerCase();

        int numberOfPage = tryParseInt(page, 0);

        if(sortAttribute != null && direction != null && direction.equalsIgnoreCase("ASC")){
            pageable = PageRequest.of(numberOfPage, MAX_SKINS_PER_PAGE, Sort.by(sortAttribute).ascending());
        }else if(sortAttribute != null && direction != null && direction.equalsIgnoreCase("DESC")){
            pageable = PageRequest.of(numberOfPage, MAX_SKINS_PER_PAGE, Sort.by(sortAttribute).descending());
        }else if(sortAttribute != null && direction == null){
            pageable = PageRequest.of(numberOfPage, MAX_SKINS_PER_PAGE, Sort.by(sortAttribute).descending());
        }else{
            pageable = PageRequest.of(numberOfPage, MAX_SKINS_PER_PAGE);
        }

        return this.skinRepository.getSkinsFiltered(name, weapon, category, pattern, rarity, pageable);

    }
}
