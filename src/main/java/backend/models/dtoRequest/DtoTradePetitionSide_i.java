package backend.models.dtoRequest;

import backend.models.MoneyPetition;
import backend.models.RequestedCrate;
import backend.models.RequestedSkin;
import backend.models.RequestedSticker;
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
    // List of the that will be in the trade petition side
    private List<RequestedSkin> skins;
    private List<RequestedSticker> stickers;
    private List<RequestedCrate> crates;
    private MoneyPetition money;
}
