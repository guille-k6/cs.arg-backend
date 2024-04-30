package backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "sticker")
public class Sticker {

    @jakarta.persistence.Id
    @Column(length = 60)
    private String id;

    @Column(length = 60)
    private String name;

    @Column(length = 512)
    private String description;

    @Column(length = 40)
    private String rarity;

    @Column(length = 256)
    private String image;
}

