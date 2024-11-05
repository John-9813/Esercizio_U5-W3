package johnoliveira.progetto_settimanale_u5_w2.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(String message, LocalDateTime timestamp) {
}
