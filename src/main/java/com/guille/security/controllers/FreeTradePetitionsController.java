package com.guille.security.controllers;

import com.guille.security.models.dtoResponse.DtoTradePetition_o;
import com.guille.security.service.TradePetitionService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade_petitions_unlogged")
public class FreeTradePetitionsController {
    private final TradePetitionService tradePetitionService;

    @Autowired
    public FreeTradePetitionsController(TradePetitionService tradePetitionService){
        this.tradePetitionService = tradePetitionService;
    }

    @GetMapping
    public ResponseEntity<?> getTradePetitions(@RequestParam(value = "page", required = false) String page)
    {
        int page_number = 0;
        if(!StringUtils.isBlank(page)) page_number = Integer.parseInt(page);
        try {
            Page<DtoTradePetition_o> tradePetitions = this.tradePetitionService.getAllUnfiltered(page_number);
            return ResponseEntity.ok(tradePetitions);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
