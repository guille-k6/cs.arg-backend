package com.guille.security.models.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoSticker_o {
    private String id;
    private String name;
    private String description;
    private String rarity;
    private String image;
}
