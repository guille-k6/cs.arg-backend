package backend.models.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTradePetition_i {
        private String description;
        private DtoTradePetitionSide_i offers;
        private DtoTradePetitionSide_i expects;
}
