package backend.models.dtoRequest;

import backend.models.dtoResponse.DtoUser_o;
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
        // TODO: Maybe I have to get the username from the JWT, otherwise anybody can create a tradepetition on anyones name
        private String jwt;
        private DtoUser_o user;
        private DtoTradePetitionSide_i offers;
        private DtoTradePetitionSide_i expects;
}
