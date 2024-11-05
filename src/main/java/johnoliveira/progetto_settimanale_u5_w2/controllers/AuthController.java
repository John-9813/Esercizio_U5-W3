package johnoliveira.progetto_settimanale_u5_w2.controllers;


import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.BadRequestException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.DipLoginDTO;
import johnoliveira.progetto_settimanale_u5_w2.payloads.DipLoginResponseDTO;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewDipendenteDTO;
import johnoliveira.progetto_settimanale_u5_w2.services.AuthService;
import johnoliveira.progetto_settimanale_u5_w2.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipLoginResponseDTO login(@RequestBody DipLoginDTO body) {
        return new DipLoginResponseDTO(this.authService.checkCredentialAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        // @Validated serve per "attivare" le regole di validazione descritte nel DTO
        // BindingResult contiene l'esito della validazione, quindi sarà utile per capire se ci sono stati errori e quali essi siano
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.dipendenteService.save(body);
    }
}

