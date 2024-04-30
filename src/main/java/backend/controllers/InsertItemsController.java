package backend.controllers;

import backend.models.Crate;
import backend.models.Skin;
import backend.models.Sticker;
import backend.models.dtoRequest.DtoCrate_i;
import backend.models.dtoRequest.DtoSkin_i;
import backend.service.CrateService;
import backend.service.SkinService;
import backend.service.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/insert")
public class InsertItemsController {
    private final SkinService skinService;
    private final StickerService stickerService;
    private final CrateService crateService;

    @Autowired
    public InsertItemsController(SkinService skinService,
                                 StickerService stickerService,
                                 CrateService crateService){
        this.skinService = skinService;
        this.stickerService = stickerService;
        this.crateService = crateService;
    }

    // POST TO INSERT SKINS
    @PostMapping("/skins")
    public ResponseEntity<String> uploadSkins(@RequestBody List<DtoSkin_i> request){
        List<Skin> skinsToBeAdded = new ArrayList<>();
        for(DtoSkin_i dts : request){
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
        try{
            this.skinService.saveAll(skinsToBeAdded);
            return ResponseEntity.ok("All good");
        }catch(Exception e){
           return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST TO INSERT STICKERS
    @PostMapping("/stickers")
    public ResponseEntity<String> uploadStickers(@RequestBody List<Sticker> request){
        // This method is different because I used all the information that
        // I had in my JSON for the sticker model, so no parse needed to be done.
        this.stickerService.saveAll(new ArrayList<>(request));
        return ResponseEntity.ok("All right xD");
    }

    // POST TO INSERT CRATES
    @PostMapping("/crates")
    public ResponseEntity<String> uploadCrates(@RequestBody List<DtoCrate_i> request){
        List<Crate> cratesToBeAdded = new ArrayList<>();

        for(DtoCrate_i dtc : request){
            Crate crate = Crate.builder()
                    .id(dtc.getId())
                    .name(dtc.getName())
                    .first_sale_date(dtc.getFirst_sale_date())
                    .image(dtc.getImage())
                    .build();
            cratesToBeAdded.add(crate);
        }
        this.crateService.saveAll(cratesToBeAdded);
        return ResponseEntity.ok("All right xD");
    }
}
