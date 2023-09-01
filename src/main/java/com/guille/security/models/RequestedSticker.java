package com.guille.security.models;

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

    @ManyToOne(optional = false)
    @JoinColumn(name="sticker_id", nullable=false)
    private Sticker sticker;

    // false = request | true = offer
    @Column(name = "trade_type")
    private Boolean tradeType;
}
