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
@Table(name = "skin")
public class Skin {
    @jakarta.persistence.Id
    @Id
    @Column(length = 60)
    private String id;

    @Column(length = 60)
    private String name;

    @Column(length = 20)
    private String weapon;

    @Column(length = 30)
    private String category;

    @Column(length = 50)
    private String pattern;

    @Column(length = 40)
    private String rarity;

    @Column(length = 256)
    private String image;
}
