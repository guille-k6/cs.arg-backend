package backend.models;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entities_requested_sequence")
    @SequenceGenerator(name = "entities_requested_sequence", sequenceName = "entities_requested_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "trade_id")
    private Long tradeId;

    @ManyToOne(optional = false)
    @JoinColumn(name="sticker_id", nullable=false)
    private Sticker sticker;

    // false = request | true = offer
    @Column(name = "trade_type")
    private Boolean tradeType;
}
