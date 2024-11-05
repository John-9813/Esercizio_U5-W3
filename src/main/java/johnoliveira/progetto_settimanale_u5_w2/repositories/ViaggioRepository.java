package johnoliveira.progetto_settimanale_u5_w2.repositories;


import johnoliveira.progetto_settimanale_u5_w2.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
    // Aggiungi metodi personalizzati qui, se necessario
}

