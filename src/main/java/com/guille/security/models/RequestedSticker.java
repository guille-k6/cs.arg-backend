package com.guille.security.models;

import com.guille.security.models.enums.PetitionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requested_sticker")
public class RequestedSticker {
    @jakarta.persistence.Id
    @Id
    @Column()
    private Long id;

    @Column(name = "trade_id")
    private Long tradeId;

    @ManyToOne(optional = false)
    @JoinColumn(name="sticker_id", nullable=false)
    private Sticker sticker;

    // 0 = request | 1 = offer
    @Column(name = "trade_type")
    private PetitionType tradeType;
}
