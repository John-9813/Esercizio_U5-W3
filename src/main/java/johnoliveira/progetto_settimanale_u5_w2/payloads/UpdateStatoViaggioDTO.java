package johnoliveira.progetto_settimanale_u5_w2.payloads;

import jakarta.validation.constraints.NotNull;
import johnoliveira.progetto_settimanale_u5_w2.entities.StatoViaggio;

// intellij mi ha fatto capire che serviva questa classe
public record UpdateStatoViaggioDTO(
        @NotNull(message = "Lo stato del viaggio è obbligatorio")
        StatoViaggio stato
) {

}
