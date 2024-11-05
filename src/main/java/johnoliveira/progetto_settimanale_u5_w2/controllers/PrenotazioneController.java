package johnoliveira.progetto_settimanale_u5_w2.controllers;

import johnoliveira.progetto_settimanale_u5_w2.entities.Prenotazione;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.BadRequestException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewPrenotazioneDTO;
import johnoliveira.progetto_settimanale_u5_w2.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    // 1. GET http://localhost:1313/prenotazioni
    @GetMapping
    public Page<Prenotazione> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioneService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:1313/prenotazioni (+ req.body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@RequestBody @Validated NewPrenotazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.prenotazioneService.save(body);
    }

    // 3. GET http://localhost:1313/prenotazioni/{prenotazioneId}
    @GetMapping("/{prenotazioneId}")
    public Prenotazione findById(@PathVariable Long prenotazioneId) {
        return this.prenotazioneService.findById(prenotazioneId);
    }

    // 4. PUT http://localhost:1313/prenotazioni/{prenotazioneId} (+ req.body)
    @PutMapping("/{prenotazioneId}")
    public Prenotazione findByIdAndUpdate(@PathVariable Long prenotazioneId,
                                          @RequestBody @Validated NewPrenotazioneDTO body,
                                          BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.prenotazioneService.findByIdAndUpdate(prenotazioneId, body);
    }

    // 5. DELETE http://localhost:1313/prenotazioni/{prenotazioneId}
    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long prenotazioneId) {
        this.prenotazioneService.findByIdAndDelete(prenotazioneId);
    }
}

