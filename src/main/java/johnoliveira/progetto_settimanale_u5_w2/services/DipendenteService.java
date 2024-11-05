package johnoliveira.progetto_settimanale_u5_w2.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.BadRequestException;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.ResourceNotFoundException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.NewDipendenteDTO;
import johnoliveira.progetto_settimanale_u5_w2.repositories.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    // salvo il dipendente con  verifica email
    public Dipendente save(NewDipendenteDTO body) {
        // 1. Verifico che l'email non sia già in uso
        this.dipendenteRepository.findByEmail(body.email()).ifPresent(
                Dipendente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        // 2. se è ok allora aggiungo i campi "server-generated" come ad esempio avatarURL
        Dipendente newDipendente = new Dipendente(body.nome(), body.cognome(), body.email(), body.username(), body.password(), "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        // 3. salvo il nuovo dipendente
        return this.dipendenteRepository.save(newDipendente);
    }

    // trovo tutti i dipendenti e gestisco elementi per paginazione
    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        // Limitiamo il numero massimo di elementi per pagina a 10
        size = Math.min(size, 10);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepository.findAll(pageable);
    }

    // trovo uno specifico dipendente
    public Dipendente findById(Long dipendenteId) {
        return dipendenteRepository.findById(dipendenteId).orElseThrow(() -> new ResourceNotFoundException("Dipendente non trovato con ID: " + dipendenteId));
    }

    // aggiorno un dipendente e verifica email
    public Dipendente findByIdAndUpdate(Long dipendenteId, NewDipendenteDTO body) {
        // 1. Trova il dipendente nel database
        Dipendente found = this.findById(dipendenteId);

        // 2. Verifica se l'email è già in uso da un altro dipendente
        if (!found.getEmail().equals(body.email())) {
            this.dipendenteRepository.findByEmail(body.email()).ifPresent(Dipendente -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        // 3. aggiorna dipendente
        found.setUsername(body.username());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setAvatarURL("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        // 4. Salva le modifiche
        return this.dipendenteRepository.save(found);
    }

    // trova un dipendente e elimina
    public void findByIdAndDelete(Long dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.dipendenteRepository.delete(found);
    }

    // upload immagine dipendente
    public String uploadAvatar(Long dipendenteId, MultipartFile file) {
        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("problemi con l'upluad del file!");
        }
        return url;
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("L'utente con la email " + email + " non è stato trovato"));
    }
}


