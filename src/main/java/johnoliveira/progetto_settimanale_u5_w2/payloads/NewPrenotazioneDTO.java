package johnoliveira.progetto_settimanale_u5_w2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewPrenotazioneDTO(
        // non mi sembrano necessari degli obblighi di @Size o regex vari..
        @NotEmpty(message = "Le preferenze sono obbligatorie!")
        String preferenze,

        @NotNull(message = "L'ID dipendente è obbligatorio!")
        Long dipendenteId,

        @NotNull(message = "L'ID viaggio è obbligatorio!")
        Long viaggioId,

        @NotNull(message = "La data è obbligatoria!")
        LocalDate data
) {
}
