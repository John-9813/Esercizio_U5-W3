package johnoliveira.progetto_settimanale_u5_w2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO(
        @NotEmpty(message = "Lo username è obbligatorio!")
        @Size(min = 3, max = 15, message = "Lo username deve esser compreso tra 3 e 15 caratteri")
        String username,
        @NotEmpty(message = "Il nome è obbligatorio!")
        @Size(min = 2, max = 20, message = "Il nome deve esser compreso tra 2 e 20 caratteri")
        String nome,
        @NotEmpty(message = "Il cognome è obbligatorio!")
        @Size(min = 2, max = 20, message = "Il cognome deve esser compreso tra 2 e 20 caratteri")
        String cognome,
        @NotEmpty(message = "La mail è obbligatoria!")
        // regex per la mail https://stackoverflow.com/questions/8204680/java-regex-email
        @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
        String email,
        @NotEmpty(message = "La password è obbligatoria")
        @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")
        String password
) {
}
