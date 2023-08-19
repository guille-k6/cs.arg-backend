package com.guille.security.controllers;

import com.guille.security.models.Sticker;
import com.guille.security.service.StickerService;
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
@RequestMapping("/api/stickers")
public class StickerController {
    private final StickerService stickerService;

    @Autowired
    public StickerController(StickerService stickerService){
        this.stickerService = stickerService;
    }

    @GetMapping()
    public ResponseEntity<?> getStickers(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "rarity", required = false) String rarity,
                                         @RequestParam(value = "page", required = false) String page,
                                         @RequestParam(value = "sort", required = false) String sortAttribute,
                                         @RequestParam(value = "direction", required = false) String direction)
    {
        // PARAMETERS THAT THE STICKER WILL BE SEARCHED BY
        HashMap<String, String> parameters = new HashMap<>();
        if(!StringUtils.isBlank(name)) parameters.put("name", name);
        if(!StringUtils.isBlank(rarity)) parameters.put("rarity", rarity);
        if(!StringUtils.isBlank(page)) parameters.put("page", (page)); // page is in string
        if(!StringUtils.isBlank(sortAttribute)) parameters.put("sortAttribute", sortAttribute);
        if(!StringUtils.isBlank(direction)) parameters.put("direction", direction);


        try {
            Page<Sticker> stickers = this.stickerService.findStickersFiltered(parameters);
            return ResponseEntity.ok(stickers);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
