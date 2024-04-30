package backend.models.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTradePetition_o {
    private Long id;
    private Long creationms;
    private String description;
    private DtoUser_o user;
    private DtoTradePetitionSide_o offers;
    private DtoTradePetitionSide_o expects;
}

