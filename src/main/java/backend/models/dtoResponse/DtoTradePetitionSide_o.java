package backend.models.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTradePetitionSide_o {
    private List<DtoSkin_o> skins;
    private List<DtoSticker_o> stickers;
    private List<DtoCrate_o> crates;
    private DtoMoney_o money;
}
