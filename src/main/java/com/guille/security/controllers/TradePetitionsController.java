package com.guille.security.controllers;

import com.guille.security.models.dtoResponse.DtoTradePetition_o;
import com.guille.security.service.TradePetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trade_petitions")
public class TradePetitionsController {
    private final TradePetitionService tradePetitionService;

    @Autowired
    public TradePetitionsController(TradePetitionService tradePetitionService){
        this.tradePetitionService = tradePetitionService;
    }

    @GetMapping
    public ResponseEntity<?> getTradePetitions()
    {
        try {
            List<DtoTradePetition_o> tradePetitions = this.tradePetitionService.getAll();
            return ResponseEntity.ok(tradePetitions);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
