package johnoliveira.progetto_settimanale_u5_w2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import johnoliveira.progetto_settimanale_u5_w2.entities.StatoViaggio;

import java.time.LocalDate;

public record NewViaggioDTO(
        @NotEmpty(message = "La destinazione è obbligatoria")
        String destinazione,

        @NotNull(message = "La data del viaggio è obbligatoria")
        LocalDate data,

        @NotNull(message = "Lo stato del viaggio è obbligatorio")
        StatoViaggio stato
) {

}

