package johnoliveira.progetto_settimanale_u5_w2.repositories;


import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    Optional<Dipendente> findByEmail(String email);
}
