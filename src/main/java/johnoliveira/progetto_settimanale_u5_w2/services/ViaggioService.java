package johnoliveira.progetto_settimanale_u5_w2.services;

import johnoliveira.progetto_settimanale_u5_w2.entities.Viaggio;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.ResourceNotFoundException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewViaggioDTO;
import johnoliveira.progetto_settimanale_u5_w2.payloads.UpdateStatoViaggioDTO;
import johnoliveira.progetto_settimanale_u5_w2.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    // creo nuovo viaggio senza verifica
    public Viaggio save(NewViaggioDTO body) {
        Viaggio viaggio = new Viaggio(body.destinazione(), body.data(), body.stato());
        return viaggioRepository.save(viaggio);
    }

    // trova tutti i viaggi + paginazione
    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }

    // Recupera un viaggio
    public Viaggio findById(Long viaggioId) {
        return viaggioRepository.findById(viaggioId).orElseThrow(() ->
                new ResourceNotFoundException("Viaggio non trovato con ID: " + viaggioId));
    }

    // aggiorna un viaggio
    public Viaggio findByIdAndUpdate(Long viaggioId, NewViaggioDTO body) {
        Viaggio viaggio = this.findById(viaggioId);
        viaggio.setDestinazione(body.destinazione());
        viaggio.setData(body.data());
        viaggio.setStato(body.stato());
        return viaggioRepository.save(viaggio);
    }

    // elimina un viaggio per ID
    public void findByIdAndDelete(Long viaggioId) {
        Viaggio viaggio = this.findById(viaggioId);
        viaggioRepository.delete(viaggio);
    }

    // modifica lo stato del viaggio
    public Viaggio updateStato(Long viaggioId, UpdateStatoViaggioDTO body) {
        Viaggio viaggio = this.findById(viaggioId);
        viaggio.setStato(body.stato());
        return viaggioRepository.save(viaggio);
    }
}

