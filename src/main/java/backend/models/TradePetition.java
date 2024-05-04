package backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trade_id")
    private List<MoneyPetition> moneyOffers = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trade_id")
    private List<RequestedSticker> requestedStickers = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trade_id")
    private List<RequestedSkin> requestedSkins = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trade_id")
    private List<RequestedCrate> requestedCrates = new LinkedList<>();

}
