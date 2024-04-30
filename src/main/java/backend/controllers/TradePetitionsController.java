package backend.controllers;

import backend.service.TradePetitionService;
import backend.models.dtoResponse.DtoTradePetition_o;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/trade_petitions")
public class TradePetitionsController {
    private final TradePetitionService tradePetitionService;
    @Autowired
    public TradePetitionsController(TradePetitionService tradePetitionService){
        this.tradePetitionService = tradePetitionService;
    }

    @GetMapping("/public")
    public ResponseEntity<?> getTradePetitions(@RequestParam(value = "page", required = false) String page) {
        try {
            Page<DtoTradePetition_o> tradePetitions = this.tradePetitionService.getAllUnfiltered(page);
            return ResponseEntity.ok(tradePetitions);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    /**
     * Gets the tradePetitions that match all the potential filters
     * @param search_type
     * @param item_type
     * @param name
     * @param weapon
     * @param rarity
     * @param condition
     * @param pattern
     * @param stattrak
     * @param souvenir
     * @param special
     * @param page
     * @param sortAttribute
     * @param direction
     * @return Json page of DtoTraPetition_o
     */
    @GetMapping("/filtered")
    public ResponseEntity<?> getTradePetitions(@RequestParam(value = "search_type", required = false) String search_type,
                                               @RequestParam(value = "item_type", required = false) String item_type,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "weapon", required = false) String weapon,
                                               @RequestParam(value = "rarity", required = false) String rarity,
                                               @RequestParam(value = "condition", required = false) String condition,
                                               @RequestParam(value = "pattern", required = false) String pattern,
                                               @RequestParam(value = "stattrak", required = false) String stattrak,
                                               @RequestParam(value = "souvenir", required = false) String souvenir,
                                               @RequestParam(value = "special", required = false) String special, // ESTRELLITA
                                               @RequestParam(value = "page", required = false) String page,
                                               @RequestParam(value = "sort", required = false) String sortAttribute,
                                               @RequestParam(value = "direction", required = false) String direction) {
            /*
            *  !! Parameters:
            *   -search_type = "PETITION" | "OFFER"
            *   -item_type  = "WEAPON | CRATE | STICKER"
            *   -direction = "ASC" | "DESC"
            */
        // PARAMETERS THAT THE TRADE PETITION WILL BE SEARCHED BY
        HashMap<String, String> parameters = new HashMap<>();
        if(!StringUtils.isBlank(search_type)) parameters.put("search_type", search_type);
        if(!StringUtils.isBlank(item_type)) parameters.put("item_type", item_type);
        if(!StringUtils.isBlank(name)) parameters.put("name", name);
        if(!StringUtils.isBlank(weapon)) parameters.put("weapon", weapon);
        if(!StringUtils.isBlank(rarity)) parameters.put("rarity", rarity); //
        if(!StringUtils.isBlank(condition)) parameters.put("condition", (condition));
        if(!StringUtils.isBlank(pattern)) parameters.put("pattern", pattern);
        if(!StringUtils.isBlank(stattrak)) parameters.put("stattrak", stattrak);
        if(!StringUtils.isBlank(souvenir)) parameters.put("souvenir", souvenir);
        if(!StringUtils.isBlank(special)) parameters.put("special", special);
        if(!StringUtils.isBlank(page)) parameters.put("page", page);
        if(!StringUtils.isBlank(sortAttribute)) parameters.put("sortAttribute", sortAttribute);
        if(!StringUtils.isBlank(direction)) parameters.put("direction", direction);
        try {
            Page<DtoTradePetition_o> tradePetitions = tradePetitionService.getTradePetitionsFiltered(parameters);
            return ResponseEntity.ok(tradePetitions);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
