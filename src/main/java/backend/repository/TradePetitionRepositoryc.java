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
                        .setParameter(2, requestedSkinId)
                        .setParameter(1, requestedSkinSticker.getId())
                        .executeUpdate();
            }
        }
    }

    @Transactional
    public void updateWithQuery(TradePetition tradePetition) {
        // Only allow to update the description of a tradePetition
        Long tradePetitionId = tradePetition.getId();
        entityManager.createNativeQuery("UPDATE trade_petition SET description = ? where id = ?")
                .setParameter(1, tradePetition.getDescription())
                .setParameter(2, tradePetitionId)
                .executeUpdate();

        for(MoneyPetition moneyPetition : tradePetition.getMoneyOffers()){
            entityManager.createNativeQuery("UPDATE money_petition SET amount = ?, country_code = ?, trade_type = ? WHERE id = ? and trade_id = ?")
                    .setParameter(1, moneyPetition.getAmount())
                    .setParameter(2, moneyPetition.getCountryCode())
                    .setParameter(3, moneyPetition.getTradeType())
                    .setParameter(4, moneyPetition.getId())
                    .setParameter(5, tradePetitionId)
                    .executeUpdate();
        }
        for(RequestedSticker requestedSticker : tradePetition.getRequestedStickers()){
            entityManager.createNativeQuery("UPDATE requested_sticker SET trade_type = ? WHERE id = ? and trade_id = ?")
                    .setParameter(1, requestedSticker.getTradeType())
                    .setParameter(2, requestedSticker.getId())
                    .setParameter(3, tradePetitionId)
                    .executeUpdate();
        }
        for(RequestedCrate requestedCrate : tradePetition.getRequestedCrates()){
            entityManager.createNativeQuery("UPDATE requested_crate SET trade_type = ? WHERE id = ? and trade_id = ?")
                    .setParameter(1, requestedCrate.getTradeType())
                    .setParameter(2, requestedCrate.getId())
                    .setParameter(3, tradePetitionId)
                    .executeUpdate();
        }
        for(RequestedSkin requestedSkin : tradePetition.getRequestedSkins()){
            entityManager.createNativeQuery("UPDATE requested_skin SET skin_id = ?, stattrak = ?, paint_pattern = ?, condition = ?, souvenir = ?, float_value = ?, trade_type = ? WHERE id ? and trade_id = ?")
                    .setParameter(1, requestedSkin.getSkin().getId())
                    .setParameter(2, requestedSkin.getStattrak())
                    .setParameter(3, requestedSkin.getPattern())
                    .setParameter(4, requestedSkin.getCondition())
                    .setParameter(5, requestedSkin.getSouvenir())
                    .setParameter(6, requestedSkin.getFloatValue())
                    .setParameter(7, requestedSkin.getTradeType())
                    .setParameter(8, requestedSkin.getId())
                    .setParameter(9, tradePetitionId)
                    .executeUpdate();
            for(Sticker requestedSkinSticker : requestedSkin.getStickers()){
                entityManager.createNativeQuery("UPDATE rskin_sticker SET sticker_id = ? WHERE requested_skin_id = ?")
                        .setParameter(1, requestedSkinSticker.getId())
                        .setParameter(2, requestedSkin.getId())
                        .executeUpdate();
            }
        }
    }

    @Transactional
    public void deleteWithQuery(TradePetition tradePetition) {
        Long tradePetitionId = tradePetition.getId();

        for(RequestedSkin requestedSkin : tradePetition.getRequestedSkins()) {
            entityManager.createNativeQuery("DELETE FROM rskin_sticker WHERE requested_skin_id = ?")
                    .setParameter(1, requestedSkin.getId())
                    .executeUpdate();
        }
        entityManager.createNativeQuery("DELETE FROM requested_skin WHERE trade_id = ?")
                .setParameter(1, tradePetitionId)
                .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM money_petition WHERE trade_id = ?")
                .setParameter(1, tradePetitionId)
                .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM requested_sticker WHERE trade_id = ?")
                .setParameter(1, tradePetitionId)
                .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM requested_crate WHERE trade_id = ?")
                .setParameter(1, tradePetitionId)
                .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM trade_petition  where id = ?")
                .setParameter(1, tradePetitionId)
                .executeUpdate();
    }
}


