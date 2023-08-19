package com.guille.security.service;

import com.guille.security.models.Crate;
import com.guille.security.repository.CrateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.guille.security.service.SkinService.tryParseInt;

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

    public Page<Crate> findCratesFiltered(HashMap<String, String> parameters){
        String name = null;
        String page = null;
        String sortAttribute = null;
        String direction = null;
        final Pageable pageable;
        final int MAX_STICKERS_PER_PAGE = 20;

        if (parameters.size() == 0){
            pageable = PageRequest.of(0, MAX_STICKERS_PER_PAGE);
            return this.crateRepository.findAll(pageable);
        }

        // If the parameter exists -> Underscores to spaces -> Assign to the variable.
        if(parameters.containsKey("name")) name = parameters.get("name").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("page")) page = parameters.get("page");
        if(parameters.containsKey("sortAttribute")) sortAttribute = parameters.get("sortAttribute").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("direction")) direction = parameters.get("direction").replaceAll("_"," ").toLowerCase();

        int numberOfPage = tryParseInt(page, 0);

        if(sortAttribute != null && direction != null && direction.equalsIgnoreCase("ASC")){
            pageable = PageRequest.of(numberOfPage, MAX_STICKERS_PER_PAGE, Sort.by(sortAttribute).ascending());
        }else if(sortAttribute != null && direction != null && direction.equalsIgnoreCase("DESC")){
            pageable = PageRequest.of(numberOfPage, MAX_STICKERS_PER_PAGE, Sort.by(sortAttribute).descending());
        }else{
            pageable = PageRequest.of(numberOfPage, MAX_STICKERS_PER_PAGE);
        }

        return this.crateRepository.getCratesFiltered(name, pageable);

    }
}
