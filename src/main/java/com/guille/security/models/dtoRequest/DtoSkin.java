package com.guille.security.models.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoSkin {
    private String id;
    private String name;
    private String description;
    private String weapon;
    private String category;
    private String pattern;
    private int min_float;
    private int max_float;
    private String rarity;
    private Boolean stattrak;
    private String paint_index;
    private LinkedList<String> wears;
    private String image;
}