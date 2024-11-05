package johnoliveira.progetto_settimanale_u5_w2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"password", "role", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities", "enabled"})
public class Dipendente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String avatarURL;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public Dipendente(String username, String nome, String cognome, String email, String avatarURL, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.avatarURL = avatarURL;
        this.password = password;
        this.role = Role.USER;  // Tutti all'inizio vengono creati come utenti "semplici"
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Questo metodo deve tornare una lista di ruoli dell'utente. Più in dettaglio vuole che venga restituita una lista di oggetti che implementano
        // GrantedAuthority. SimpleGrantedAuthority è una classe che rappresenta i ruoli degli utenti nel mondo Spring Security
        // ed implementa GrantedAuthority, quindi dobbiamo prendere il nostro ruolo (enum) e passare il name()
        // di quel ruolo al costruttore dell'oggetto
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override // Obligatorio
    public String getUsername() {
        return this.getUsername();
    }
}
