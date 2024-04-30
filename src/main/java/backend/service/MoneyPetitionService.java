package backend.service;

import backend.repository.MoneyPetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyPetitionService {
    private final MoneyPetitionRepository moneyPetitionRepository;

    @Autowired
    public MoneyPetitionService(MoneyPetitionRepository moneyPetitionRepository){
        this.moneyPetitionRepository = moneyPetitionRepository;
    }
}
