package com.guille.security.models.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoCrate_i {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkinContained {
        private String id;
        private String name;
        private String rarity;
    }

    private String id;
    private String name;
    private String description;
    private String type;
    private String first_sale_date;
    private ArrayList<SkinContained> contains;
    private ArrayList<SkinContained> contains_rare;
    private String image;
}
