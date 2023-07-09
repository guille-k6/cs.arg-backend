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
@Table(name = "trade_petition")
public class TradePetition {
    @jakarta.persistence.Id
    @Id
    @Column()
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "creation_ms")
    private Long creationMs;

    private String description;

    @OneToMany(mappedBy = "trade_petition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoneyPetition> moneyOffers = new ArrayList<>();

}
