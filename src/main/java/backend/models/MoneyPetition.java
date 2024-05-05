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
@Table(name = "money_petition")
public class MoneyPetition {
    @jakarta.persistence.Id
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_petition_sequence")
    @SequenceGenerator(name = "money_petition_sequence", sequenceName = "money_petition_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "trade_id")
    private Long tradeId;

    @Column()
    private Long amount;

    @Column(name = "country_code")
    private String countryCode;

    // 0 = request | 1 = offer
    @Column(name = "trade_type")
    private Boolean tradeType;
}
