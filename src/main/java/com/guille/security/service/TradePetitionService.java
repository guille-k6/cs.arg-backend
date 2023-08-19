package com.guille.security.service;

import com.guille.security.models.*;
import com.guille.security.models.dtoResponse.*;
import com.guille.security.models.enums.PetitionType;
import com.guille.security.repository.TradePetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TradePetitionService {

    private final TradePetitionRepository tradePetitionRepository;
    @Autowired
    public TradePetitionService(TradePetitionRepository tradePetitionRepository){
        this.tradePetitionRepository = tradePetitionRepository;
    }

    public LinkedList<DtoTradePetition_o> parsePetitionsOut(List<TradePetition> tradePetitions){

        LinkedList<DtoTradePetition_o> dtoTradePetitions = new LinkedList<>();

        for(TradePetition tp : tradePetitions){
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
                        .category(skin.getSkin().getCategory())
                        .float_value(skin.getFloatValue())
                        .float_min(skin.getFloatMin())
                        .float_max(skin.getFloatMax())
                        .pattern(skin.getPattern())
                        .stickers(newStickers)
                        .build();

                if(skin.getTradeType() == PetitionType.PETITION){
                    requestedSkins.add(newSkin);
                }else if(skin.getTradeType() == PetitionType.OFFER){
                    offeredSkins.add(newSkin);
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

                if(sticker.getTradeType() == PetitionType.PETITION){
                    requestedStickers.add(newSticker);
                }else if(sticker.getTradeType() == PetitionType.OFFER){
                    offeredStickers.add(newSticker);
                }
            }

            // For each crate in my tradePetition
            for(RequestedCrate crate : tp.getRequestedCrates()){
                DtoCrate_o newCrate = DtoCrate_o.builder()
                        .id(crate.getCrate().getId())
                        .name(crate.getCrate().getName())
                        .image(crate.getCrate().getImage())
                        .build();

                if(crate.getTradeType() == PetitionType.PETITION){
                    requestedCrates.add(newCrate);
                }else if(crate.getTradeType() == PetitionType.OFFER){
                    offeredCrates.add(newCrate);
                }
            }

            // For each moneyOffer in my tradePetition
            for(MoneyPetition moneyPetition : tp.getMoneyOffers()){
                DtoMoney_o newMoneyPetition = DtoMoney_o.builder()
                        .amount(moneyPetition.getAmount())
                        .country_code(moneyPetition.getCountryCode())
                        .build();

                if(moneyPetition.getTradeType() == PetitionType.PETITION){
                    requestedMoney = newMoneyPetition;
                }else if(moneyPetition.getTradeType() == PetitionType.OFFER){
                    offeredMoney = newMoneyPetition;
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

            dtoTradePetitions.add(tradePetition);
        }

        return dtoTradePetitions;
    }
    public List<DtoTradePetition_o> getAll() {
        // tradePetitions in my database
        List<TradePetition> tradePetitions = this.tradePetitionRepository.findAll();
        // dtoTradePetitions that I will return
        return this.parsePetitionsOut(tradePetitions);
    }
}
