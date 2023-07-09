package com.guille.security.service;

import com.guille.security.models.MoneyPetition;
import com.guille.security.models.Skin;
import com.guille.security.repository.MoneyPetitionRepository;
import com.guille.security.repository.SkinRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyPetitionService {
    private final MoneyPetitionRepository moneyPetitionRepository;

    @Autowired
    public MoneyPetitionService(MoneyPetitionRepository moneyPetitionRepository){
        this.moneyPetitionRepository = moneyPetitionRepository;
    }
}
