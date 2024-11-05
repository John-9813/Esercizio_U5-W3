package johnoliveira.progetto_settimanale_u5_w2.exceptions;

import johnoliveira.progetto_settimanale_u5_w2.payloads.ErrorsRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsRespDTO handleBadrequest(BadRequestException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorsRespDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsRespDTO handleNotFound(ResourceNotFoundException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsRespDTO handleGeneric(Exception ex) {
        ex.printStackTrace(); // per trackare l'eccezione
        return new ErrorsRespDTO("Problemi al server. Riprova più tardi", LocalDateTime.now());
    }
}
