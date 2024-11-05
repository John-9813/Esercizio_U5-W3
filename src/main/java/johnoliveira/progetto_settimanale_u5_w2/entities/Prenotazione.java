package johnoliveira.progetto_settimanale_u5_w2.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String preferenze;

    @ManyToOne
    @JoinColumn(name = "id_dipendente", nullable = false)
    private Dipendente dipendente;

    @OneToOne
    @JoinColumn(name = "id_viaggio", nullable = false)
    private Viaggio viaggio;

    @Column(nullable = false)
    private LocalDate data;

    public Prenotazione(String preferenze, Dipendente dipendente, Viaggio viaggio, LocalDate data) {
        this.preferenze = preferenze;
        this.dipendente = dipendente;
        this.viaggio = viaggio;
        this.data = data;
    }
}

