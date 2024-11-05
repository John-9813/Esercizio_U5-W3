package johnoliveira.progetto_settimanale_u5_w2.controllers;

import johnoliveira.progetto_settimanale_u5_w2.entities.Viaggio;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.BadRequestException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewViaggioDTO;
import johnoliveira.progetto_settimanale_u5_w2.payloads.UpdateStatoViaggioDTO;
import johnoliveira.progetto_settimanale_u5_w2.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    // 1. GET http://localhost:1313/viaggi
    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return this.viaggioService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:1313/viaggi
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated NewViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.viaggioService.save(body);
    }

    // 3. GET http://localhost:1313/viaggi/{viaggioId}
    @GetMapping("/{viaggioId}")
    public Viaggio findById(@PathVariable Long viaggioId) {
        return this.viaggioService.findById(viaggioId);
    }

    // 4. PUT http://localhost:1313/viaggi/{viaggioId}
    @PutMapping("/{viaggioId}")
    public Viaggio findByIdAndUpdate(@PathVariable Long viaggioId,
                                     @RequestBody @Validated NewViaggioDTO body,
                                     BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.viaggioService.findByIdAndUpdate(viaggioId, body);
    }

    // 5.  DELETE http://localhost:1313/viaggi/{viaggioId}
    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long viaggioId) {
        this.viaggioService.findByIdAndDelete(viaggioId);
    }

    // 6. PATCH http://localhost:1313/viaggi/{viaggioId}/stato
    @PatchMapping("/{viaggioId}/stato")
    public Viaggio updateStato(@PathVariable Long viaggioId,
                               @RequestBody @Validated UpdateStatoViaggioDTO body,
                               BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.viaggioService.updateStato(viaggioId, body);
    }
}

