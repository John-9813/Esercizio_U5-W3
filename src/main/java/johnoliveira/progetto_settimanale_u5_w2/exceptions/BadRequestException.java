package johnoliveira.progetto_settimanale_u5_w2.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
