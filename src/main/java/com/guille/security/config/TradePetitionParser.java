package com.guille.security.config;

import com.guille.security.models.*;
import com.guille.security.models.dtoResponse.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * The purpose of this class is to have a method that takes a TradePetition as an input
 * and returns an instance of the DTO_TradePetition. See usage -->
 * tradePetitions.map(tradePetitionParser::tradePetitionToDTO);
 **/
@Component
public class TradePetitionParser {
    public DtoTradePetition_o tradePetitionToDTO(TradePetition tp) {

        DtoTradePetition_o tradePetition = new DtoTradePetition_o();

        tradePetition.setId(tp.getId());
        tradePetition.setCreationms(tp.getCreationMs());
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
}
