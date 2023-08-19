package com.guille.security.controllers;

import com.guille.security.models.Skin;
import com.guille.security.service.SkinService;
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
@RequestMapping("/api/skins")
public class SkinController {
    private final SkinService skinService;

    @Autowired
    public SkinController(SkinService skinService){
        this.skinService = skinService;
    }

    @GetMapping()
    public ResponseEntity<?> getSkins(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "weapon", required = false) String weapon,
                                    @RequestParam(value = "category", required = false) String category,
                                    @RequestParam(value = "pattern", required = false) String pattern,
                                    @RequestParam(value = "rarity", required = false) String rarity,
                                    @RequestParam(value = "page", required = false) String page,
                                    @RequestParam(value = "sort", required = false) String sortAttribute,
                                    @RequestParam(value = "direction", required = false) String direction)
    {
        // PARAMETERS THAT THE SKIN WILL BE SEARCHED BY
        HashMap<String, String> parameters = new HashMap<>();
        if(!StringUtils.isBlank(name)) parameters.put("name", name);
        if(!StringUtils.isBlank(weapon)) parameters.put("weapon", weapon);
        if(!StringUtils.isBlank(category)) parameters.put("category", category);
        if(!StringUtils.isBlank(pattern)) parameters.put("pattern", pattern);
        if(!StringUtils.isBlank(rarity)) parameters.put("rarity", rarity);
        if(!StringUtils.isBlank(page)) parameters.put("page", (page)); // page is in string
        if(!StringUtils.isBlank(sortAttribute)) parameters.put("sortAttribute", sortAttribute);
        if(!StringUtils.isBlank(direction)) parameters.put("direction", direction);


        try {
            Page<Skin> skins = this.skinService.findSkinsFiltered(parameters);
            return ResponseEntity.ok(skins);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
