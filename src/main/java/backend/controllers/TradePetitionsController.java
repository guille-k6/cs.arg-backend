package backend.controllers;

import backend.config.TradePetitionParser;
import backend.models.TradePetition;
import backend.models.dtoRequest.DtoTradePetition_i;
import backend.service.TradePetitionService;
import backend.models.dtoResponse.DtoTradePetition_o;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/trade_petitions")
public class TradePetitionsController {
    private final TradePetitionService tradePetitionService;
    private final TradePetitionParser tradePetitionParser;
    @Autowired
    public TradePetitionsController(TradePetitionService tradePetitionService, TradePetitionParser tradePetitionParser){
        this.tradePetitionService = tradePetitionService;
        this.tradePetitionParser = tradePetitionParser;
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
     * @param search_type "PETITION" or "OFFER"
     * @param item_type "WEAPON | CRATE | STICKER"
     * @param name
     * @param weapon
     * @param rarity
     * @param condition
     * @param pattern
     * @param stattrak
     * @param souvenir
     * @param special for items like knives or gloves
     * @param page
     * @param sortAttribute
     * @param direction "ASC" | "DESC"
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
                                               @RequestParam(value = "special", required = false) String special,
                                               @RequestParam(value = "page", required = false) String page,
                                               @RequestParam(value = "sort", required = false) String sortAttribute,
                                               @RequestParam(value = "direction", required = false) String direction) {
        HashMap<String, String> parameters = new HashMap<>();
        if(!StringUtils.isBlank(search_type)) parameters.put("search_type", search_type);
        if(!StringUtils.isBlank(item_type)) parameters.put("item_type", item_type);
        if(!StringUtils.isBlank(name)) parameters.put("name", name);
        if(!StringUtils.isBlank(weapon)) parameters.put("weapon", weapon);
        if(!StringUtils.isBlank(rarity)) parameters.put("rarity", rarity);
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

    @PostMapping("/create")
    public ResponseEntity<?> createTradePetition(@RequestHeader("Authorization") String jwt, @RequestBody DtoTradePetition_i dtoTradePetition){
        String tpJwt = jwt.split("Bearer ")[1];
        TradePetition tradePetition = tradePetitionParser.dtoToTradePetition(dtoTradePetition, tpJwt);
        try {
            tradePetitionService.createTradePetition(tradePetition);
            return ResponseEntity.ok("Peticion de intercambio creada correctamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo crear la peticion de intercambio: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTradePetition(@RequestHeader("Authorization") String jwt, @RequestBody DtoTradePetition_i dtoTradePetition){
        String tpJwt = jwt.split("Bearer ")[1];
        TradePetition tradePetition = tradePetitionParser.dtoToTradePetition(dtoTradePetition, tpJwt);
        try {
            tradePetitionService.updateTradePetition(tradePetition);
            return ResponseEntity.ok("Peticion de intercambio actualizada correctamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo actualizar la peticion de intercambio: " + e.getMessage());
        }
    }
}




