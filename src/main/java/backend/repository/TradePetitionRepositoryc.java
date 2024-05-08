package backend.repository;

import backend.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class TradePetitionRepositoryc {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(TradePetition tradePetition) {
        entityManager.createNativeQuery("INSERT INTO trade_petition(user_id, description, creation_ms) values(?,?,?)")
                .setParameter(1, tradePetition.getUser().getId())
                .setParameter(2, tradePetition.getDescription())
                .setParameter(3, tradePetition.getCreationMs())
                .executeUpdate();
        Long tradePetitionId = ((Number) entityManager.createNativeQuery("SELECT lastval()").getSingleResult()).longValue();

        for(MoneyPetition moneyPetition : tradePetition.getMoneyOffers()){
            entityManager.createNativeQuery("INSERT INTO money_petition(trade_id, amount, country_code, trade_type) values(?,?,?,?)")
                    .setParameter(1, tradePetitionId)
                    .setParameter(2, moneyPetition.getAmount())
                    .setParameter(3, moneyPetition.getCountryCode())
                    .setParameter(4, moneyPetition.getTradeType())
                    .executeUpdate();
        }
        for(RequestedSticker requestedSticker : tradePetition.getRequestedStickers()){
            entityManager.createNativeQuery("INSERT INTO requested_sticker(trade_id, sticker_id, trade_type) values(?,?,?)")
                    .setParameter(1, tradePetitionId)
                    .setParameter(2, requestedSticker.getSticker().getId())
                    .setParameter(3, requestedSticker.getTradeType())
                    .executeUpdate();
        }
        for(RequestedCrate requestedCrate : tradePetition.getRequestedCrates()){
            entityManager.createNativeQuery("INSERT INTO requested_crate(trade_id, sticker_id, trade_type) values(?,?,?)")
                    .setParameter(1, tradePetitionId)
                    .setParameter(2, requestedCrate.getCrate().getId())
                    .setParameter(3, requestedCrate.getTradeType())
                    .executeUpdate();
        }
        for(RequestedSkin requestedSkin : tradePetition.getRequestedSkins()){
            entityManager.createNativeQuery("INSERT INTO requested_skin(trade_id, skin_id, stattrak, paint_pattern, condition, souvenir, float_value, trade_type) values(?,?,?,?,?,?,?,?)")
                    .setParameter(1, tradePetitionId)
                    .setParameter(2, requestedSkin.getSkin().getId())
                    .setParameter(3, requestedSkin.getStattrak())
                    .setParameter(4, requestedSkin.getPattern())
                    .setParameter(5, requestedSkin.getCondition())
                    .setParameter(6, requestedSkin.getSouvenir())
                    .setParameter(7, requestedSkin.getFloatValue())
                    .setParameter(8, requestedSkin.getTradeType())
                    .executeUpdate();
            Long requestedSkinId = ((Number) entityManager.createNativeQuery("SELECT lastval()").getSingleResult()).longValue();
            for(Sticker requestedSkinSticker : requestedSkin.getStickers()){
                entityManager.createNativeQuery("INSERT INTO rskin_sticker(sticker_id, requested_skin_id) values(?,?)")
                        .setParameter(1, requestedSkinId)
                        .setParameter(2, requestedSkinSticker.getId())
                        .executeUpdate();
            }
        }
    }
}


