package backend.config;

import backend.models.*;
import backend.models.dtoRequest.DtoTradePetitionSide_i;
import backend.models.dtoRequest.DtoTradePetition_i;
import backend.models.dtoResponse.*;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The purpose of this class is to Map from TradePetitions to their respectivo input or output Dto
 **/
@Component
public class TradePetitionParser {
    private final JwtService jwtService;
    private final UserService userService;
    @Autowired
    public TradePetitionParser(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public DtoTradePetition_o tradePetitionToDTO(TradePetition tp) {

        DtoTradePetition_o tradePetition = new DtoTradePetition_o();

        tradePetition.setId(tp.getId());
        tradePetition.setCreationms(tp.getCreationMs());
        tradePetition.setDescription(tp.getDescription());
        DtoUser_o dtoUser = DtoUser_o.builder()
                .nickname(tp.getUser().getNickname())
                .build();
        tradePetition.setUser(dtoUser);

        // Offer side collections of the tradePetition
        ArrayList<DtoSkin_o> offeredSkins = new ArrayList<>();
        ArrayList<DtoSticker_o> offeredStickers = new ArrayList<>();
        ArrayList<DtoCrate_o> offeredCrates = new ArrayList<>();
        DtoMoney_o offeredMoney = new DtoMoney_o();

        // Request side collections of the tradePetition
        ArrayList<DtoSkin_o> requestedSkins = new ArrayList<>();
        ArrayList<DtoSticker_o> requestedStickers = new ArrayList<>();
        ArrayList<DtoCrate_o> requestedCrates = new ArrayList<>();
        DtoMoney_o requestedMoney = new DtoMoney_o();

        // For each skin in my tradePetition
        for(RequestedSkin skin : tp.getRequestedSkins()){

            // For each sticker in my skin of my tradePetition
            ArrayList<DtoSticker_o> newStickers = new ArrayList<>();
            for(Sticker sticker : skin.getStickers()){
                DtoSticker_o newSticker = DtoSticker_o.builder()
                        .id(sticker.getId())
                        .name(sticker.getName())
                        .description(sticker.getDescription())
                        .image(sticker.getImage())
                        .rarity(sticker.getRarity())
                        .build();
                newStickers.add(newSticker);
            }

            // Building the queried skin
            DtoSkin_o newSkin = DtoSkin_o.builder()
                    .id(skin.getSkin().getId())
                    .weapon(skin.getSkin().getWeapon())
                    .name(skin.getSkin().getPattern())
                    .image(skin.getSkin().getImage())
                    .rarity(skin.getSkin().getRarity())
                    .condition(skin.getCondition())
                    .stattrak(skin.getStattrak())
                    .souvenir(skin.getSouvenir())
                    .float_value(skin.getFloatValue())
                    .pattern(skin.getPattern())
                    .stickers(newStickers)
                    .build();

            if(skin.getTradeType()){
                offeredSkins.add(newSkin);
            }else{
                requestedSkins.add(newSkin);
            }
        }

        // For each sticker in my tradePetition
        for(RequestedSticker sticker : tp.getRequestedStickers()){
            DtoSticker_o newSticker = DtoSticker_o.builder()
                    .id(sticker.getSticker().getId())
                    .name(sticker.getSticker().getName())
                    .description(sticker.getSticker().getDescription())
                    .rarity(sticker.getSticker().getRarity())
                    .image(sticker.getSticker().getImage())
                    .build();

            if(sticker.getTradeType()){
                offeredStickers.add(newSticker);
            }else{
                requestedStickers.add(newSticker);
            }
        }

        // For each crate in my tradePetition
        for(RequestedCrate crate : tp.getRequestedCrates()){
            DtoCrate_o newCrate = DtoCrate_o.builder()
                    .id(crate.getCrate().getId())
                    .name(crate.getCrate().getName())
                    .image(crate.getCrate().getImage())
                    .build();

            if(crate.getTradeType()){ // true = offer; false = petition
                offeredCrates.add(newCrate);
            }else{
                requestedCrates.add(newCrate);
            }
        }

        // For each moneyOffer in my tradePetition
        for(MoneyPetition moneyPetition : tp.getMoneyOffers()){
            DtoMoney_o newMoneyPetition = DtoMoney_o.builder()
                    .amount(moneyPetition.getAmount())
                    .country_code(moneyPetition.getCountryCode())
                    .build();

            if(moneyPetition.getTradeType()){
                offeredMoney = newMoneyPetition;
            }else{
                requestedMoney = newMoneyPetition;
            }
        }

        // Now I can build each side of my tradePetition
        DtoTradePetitionSide_o offer = DtoTradePetitionSide_o.builder()
                .skins(offeredSkins)
                .stickers(offeredStickers)
                .crates(offeredCrates)
                .money(offeredMoney)
                .build();

        DtoTradePetitionSide_o request = DtoTradePetitionSide_o.builder()
                .skins(requestedSkins)
                .stickers(requestedStickers)
                .crates(requestedCrates)
                .money(requestedMoney)
                .build();

        tradePetition.setOffers(offer);
        tradePetition.setExpects(request);

        return tradePetition;
    }

    public TradePetition DtoToTradePetition(DtoTradePetition_i dtoTradePetition){
        String tpJwt = dtoTradePetition.getJwt();
        String username = jwtService.extractUsername(tpJwt);
        User tpUser = userService.loadUserByEmail(username);

        TradePetition tradePetition = new TradePetition();
        tradePetition.setUser(tpUser);
        tradePetition.setCreationMs(System.currentTimeMillis());
        tradePetition.setDescription(dtoTradePetition.getDescription());
        List<Skin> requestedSkins = new LinkedList<>();
        List<Sticker> requestedStickers = new LinkedList<>();
        List<Crate> requestedCrated = new LinkedList<>();

        // Expect side of the dtoTradePetition
        DtoTradePetitionSide_i expectSide = dtoTradePetition.getExpects();
        for (String skinId : expectSide.getSkins()){
            RequestedSkin expectedSkin = RequestedSkin.builder().id(skinId).
        }

        return tradePetition;
    }
}
