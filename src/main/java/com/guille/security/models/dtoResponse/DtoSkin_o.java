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
public class DtoSkin_o {
    private String id;
    private String weapon;
    private String name;
    private String image;
    private String rarity;
    private String condition;
    private Boolean stattrak;
    private Boolean souvenir;
    private Integer float_value;
    private Integer pattern;
    private ArrayList<DtoSticker_o> stickers;
}
