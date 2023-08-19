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
@Table(name = "money_petition")
public class MoneyPetition {
    @jakarta.persistence.Id
    @Id
    @Column()
    private Long id;

    @Column()
    private Long amount;

    @Column(name = "country_code")
    private String countryCode;

    // 0 = request | 1 = offer
    @Column(name = "trade_type")
    private PetitionType tradeType;
}
