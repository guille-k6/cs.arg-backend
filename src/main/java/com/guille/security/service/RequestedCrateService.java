package com.guille.security.service;

import com.guille.security.models.Crate;
import com.guille.security.models.RequestedCrate;
import com.guille.security.repository.CrateRepository;
import com.guille.security.repository.RequestedCrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestedCrateService {
    private final RequestedCrateRepository requestedCrateRepository;

    @Autowired
    public RequestedCrateService(RequestedCrateRepository requestedCrateRepository){
        this.requestedCrateRepository = requestedCrateRepository;
    }

    public List<RequestedCrate> findAll(){
        return this.requestedCrateRepository.findAll();
    }
}
