package com.guille.security.models;

import com.guille.security.models.enums.PetitionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requested_skin")
public class RequestedSkin {
    @jakarta.persistence.Id
    @Id
    @Column()
    private Long id;

    @Column(name = "trade_id")
    private Long tradeId;

    @ManyToOne(optional = false)
    @JoinColumn(name="skin_id", nullable=false)
    private Skin skin;

    @ManyToMany
    @JoinTable(
            name = "rskin_sticker",
            joinColumns = @JoinColumn(name = "requested_skin_id"),
            inverseJoinColumns = @JoinColumn(name = "sticker_id")
    )
    private List<Sticker> stickers = new ArrayList<>();

    @Column(name = "float_value")
    private Integer floatValue;

    @Column(name = "float_min")
    private Integer floatMin;

    @Column(name = "float_max")
    private Integer floatMax;

    private Boolean stattrak;

    private Integer pattern; // this paint pattern goes from 0-999

    private Boolean souvenir;

    // 0 = request | 1 = offer
    @Column(name = "trade_type")
    private PetitionType tradeType;
}
