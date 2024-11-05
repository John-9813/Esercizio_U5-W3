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
public class Viaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinazione;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoViaggio stato;

    public Viaggio(String destinazione, LocalDate data, StatoViaggio stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }
}
