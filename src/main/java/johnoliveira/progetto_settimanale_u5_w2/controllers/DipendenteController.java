package johnoliveira.progetto_settimanale_u5_w2.controllers;


import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.BadRequestException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewDipendenteDTO;
import johnoliveira.progetto_settimanale_u5_w2.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    // 1. GET http://localhost:1313/dipendenti
    @GetMapping
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:1313/dipendenti (+ req.body) --> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.dipendenteService.save(body);
    }

    // 3. GET http://localhost:1313/dipendenti/{dipendenteId}
    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable Long dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    // 4. PUT http://localhost:1313/dipendenti/{dipendenteId} (+ req.body)
    @PutMapping("/{dipendenteId}")
    public Dipendente findByIdAndUpdate(@PathVariable Long dipendenteId,
                                        @RequestBody @Validated NewDipendenteDTO body,
                                        BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    // 5. DELETE http://localhost:1313/dipendenti/{dipendenteId} --> 204
    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }


    // 6. PATCH per cambiare immagine http://localhost:1313/dipendenti/{dipendenteId}/avatar
    @PatchMapping("/{dipendenteId}/avatar")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String uploadAvatar(@PathVariable Long dipendenteId, @RequestParam("avatar") MultipartFile file) {
        return this.dipendenteService.uploadAvatar(dipendenteId, file);
    }
}


