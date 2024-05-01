package backend.models.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTradePetitionSide_i {
    // List of the items-id that will be in the trade petition side
    private List<String> skins;
    private List<String> stickers;
    private List<String> crates;
    private Double money;
}
