package com.guille.security.demo;

import com.guille.security.auth.AuthenticationResponse;
import com.guille.security.auth.RegisterRequest;
import com.guille.security.models.Crate;
import com.guille.security.models.Skin;
import com.guille.security.models.Sticker;
import com.guille.security.models.dtoRequest.DtoCrate;
import com.guille.security.models.dtoRequest.DtoSkin;
import com.guille.security.service.CrateService;
import com.guille.security.service.RequestedCrateService;
import com.guille.security.service.SkinService;
import com.guille.security.service.StickerService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    private final SkinService skinService;
    private final StickerService stickerService;
    private final CrateService crateService;
    private final RequestedCrateService requestedCrateService;

    @Autowired
    public DemoController(SkinService skinService,
                          StickerService stickerService,
                          CrateService crateService,
                          RequestedCrateService requestedCrateService){
        this.skinService = skinService;
        this.stickerService = stickerService;
        this.crateService = crateService;
        this.requestedCrateService = requestedCrateService;
    }

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/requestedCrate")
    public ResponseEntity<String> listRequestedCrates(){
        return ResponseEntity.ok(this.requestedCrateService.findAll().toString());
    }


    // TRAE LAS SKINS FILTRADAS: IMPORTANTE
    // FALTA ACOMODAR: MAYUSCULAS Y MINUSCULAS, ESPACIOS Y GUIONES BAJOS.
    @GetMapping("/skins")
    public ResponseEntity<?> getSkins(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "weapon", required = false) String weapon,
                                               @RequestParam(value = "category", required = false) String category,
                                               @RequestParam(value = "pattern", required = false) String pattern,
                                               @RequestParam(value = "rarity", required = false) String rarity)
    {
        // PARAMETERS THAT THE SKIN WILL BE SEARCHED BY
        HashMap<String, String> parameters = new HashMap<>();
        if(!StringUtils.isBlank(name)) parameters.put("name", name);
        if(!StringUtils.isBlank(weapon)) parameters.put("weapon", weapon);
        if(!StringUtils.isBlank(category)) parameters.put("category", category);
        if(!StringUtils.isBlank(pattern)) parameters.put("pattern", pattern);
        if(!StringUtils.isBlank(rarity)) parameters.put("rarity", rarity);


        try {
            List<Skin> skins = this.skinService.findSkinsFiltered(parameters);
            return ResponseEntity.ok(skins);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

        /*List<Skin> skins = this.skinService.findSkinsFiltered(parameters);
        return ResponseEntity.ok(skins);*/
    }

    // POST TO INSERT SKINS
    @PostMapping("/skins")
    public ResponseEntity<String> uploadSkins(@RequestBody List<DtoSkin> request){
        List<Skin> skinsToBeAdded = new ArrayList<>();
        for(DtoSkin dts : request){
            Skin s = Skin.builder()
                    .id(dts.getId())
                    .name(dts.getName())
                    .weapon(dts.getWeapon())
                    .category(dts.getCategory())
                    .pattern(dts.getPattern())
                    .rarity(dts.getRarity())
                    .image(dts.getImage())
                    .build();
            skinsToBeAdded.add(s);
        }
        this.skinService.saveAll(skinsToBeAdded);
        return ResponseEntity.ok(this.skinService.findAll().toString());
    }

    // POST TO INSERT STICKERS
    @PostMapping("/stickers")
    public ResponseEntity<String> uploadStickers(@RequestBody List<Sticker> request){
        List<Sticker> stickersToBeAdded = new ArrayList<>();
        for(Sticker s : request){
            Sticker stick = Sticker.builder()
                    .id(s.getId())
                    .name(s.getName())
                    .description(s.getDescription())
                    .rarity(s.getRarity())
                    .image(s.getImage())
                    .build();
            stickersToBeAdded.add(s);
        }
        this.stickerService.saveAll(stickersToBeAdded);
        return ResponseEntity.ok(this.stickerService.findAll().toString());
    }

    // POST TO INSERT CRATES
    @PostMapping("/crates")
    public ResponseEntity<String> uploadCrates(@RequestBody List<DtoCrate> request){
        List<Crate> cratesToBeAdded = new ArrayList<>();

        for(DtoCrate dtc : request){
                Crate crate = Crate.builder()
                        .id(dtc.getId())
                        .name(dtc.getName())
                        .first_sale_date(dtc.getFirst_sale_date())
                        .image(dtc.getImage())
                        .build();
                cratesToBeAdded.add(crate);
        }
        this.crateService.saveAll(cratesToBeAdded);
        return ResponseEntity.ok(this.crateService.findAll().toString());
    }

}
