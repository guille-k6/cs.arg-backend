package com.guille.security.models.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTradePetitionSide_o {
    private ArrayList<DtoSkin_o> skins;
    private ArrayList<DtoSticker_o> stickers;
    private ArrayList<DtoCrate_o> crates;
    private DtoMoney_o money;
}
