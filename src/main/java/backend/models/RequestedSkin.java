package backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.LinkedList;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entities_requested_sequence")
    @SequenceGenerator(name = "entities_requested_sequence", sequenceName = "entities_requested_sequence", allocationSize = 1)
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
    private List<Sticker> stickers = new LinkedList<>();

    @Column(name = "float_value")
    private Double floatValue;

    private String condition;

    private Boolean stattrak;

    @Column(name = "paint_pattern")
    private Integer pattern; // this paint pattern goes from 0-999

    private Boolean souvenir;

    // false = request | true = offer
    @Column(name = "trade_type")
    private Boolean tradeType;
}
