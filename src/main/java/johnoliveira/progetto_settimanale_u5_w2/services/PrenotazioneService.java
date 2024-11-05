package johnoliveira.progetto_settimanale_u5_w2.services;

import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import johnoliveira.progetto_settimanale_u5_w2.entities.Prenotazione;
import johnoliveira.progetto_settimanale_u5_w2.entities.Viaggio;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.ConflictException;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.ResourceNotFoundException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewPrenotazioneDTO;
import johnoliveira.progetto_settimanale_u5_w2.repositories.DipendenteRepository;
import johnoliveira.progetto_settimanale_u5_w2.repositories.PrenotazioneRepository;
import johnoliveira.progetto_settimanale_u5_w2.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        return prenotazioneRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    // salva prenotazione con controlli
    public Prenotazione save(NewPrenotazioneDTO body) {
        Dipendente dipendente = dipendenteRepository.findById(body.dipendenteId()).orElseThrow(() ->
                new ResourceNotFoundException("Dipendente " + body.dipendenteId() + " non trovato"));
        Viaggio viaggio = viaggioRepository.findById(body.viaggioId()).orElseThrow(() ->
                new ResourceNotFoundException("Viaggio" + body.viaggioId() + " non trovato"));

        // controllo se esiste già la prenotazione per la data
        Optional<Prenotazione> prenotazioneEsistente = prenotazioneRepository.findByDipendenteAndData(dipendente, body.data());
        if (prenotazioneEsistente.isPresent()) {
            throw new ConflictException("Esiste già una prenotazione per questo dipendente nella stessa data.");
        }

        Prenotazione prenotazione = new Prenotazione(
                body.preferenze(),
                dipendente,
                viaggio,
                body.data()
        );
        // se ok, salva
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione findById(Long prenotazioneId) {
        return prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new ResourceNotFoundException("Prenotazione non trovata con ID:" + prenotazioneId));
    }

    // trova e aggiorna dipendente
    public Prenotazione findByIdAndUpdate(Long prenotazioneId, NewPrenotazioneDTO body) {
        Prenotazione prenotazione = this.findById(prenotazioneId);
        // controlli se esistono le entità cercate
        Dipendente dipendente = dipendenteRepository.findById(body.dipendenteId()).orElseThrow(() ->
                new ResourceNotFoundException("Dipendente non trovato"));
        Viaggio viaggio = viaggioRepository.findById(body.viaggioId()).orElseThrow(() ->
                new ResourceNotFoundException("Viaggio non trovato"));

        // sempre controllo prenotazione già esistente per la stessa datta
        Optional<Prenotazione> prenotazioneEsistente = prenotazioneRepository.findByDipendenteAndData(dipendente, body.data());
        if (prenotazioneEsistente.isPresent()) {
            throw new ConflictException("Esiste già una prenotazione per questo dipendente nella stessa data.");
        }

        // aggiorna prenotazione
        prenotazione.setPreferenze(body.preferenze());
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setData(body.data());

        return prenotazioneRepository.save(prenotazione);
    }

    // trova e elimina prenotazione
    public void findByIdAndDelete(Long prenotazioneId) {
        Prenotazione prenotazione = this.findById(prenotazioneId);
        prenotazioneRepository.delete(prenotazione);
    }


}

