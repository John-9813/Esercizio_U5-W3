package johnoliveira.progetto_settimanale_u5_w2.entities;

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
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Questo metodo deve tornare una lista di ruoli utente.
        // In dettaglio vuole una lista di oggetti che implementano GFrantedAuthority.
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override // Obligatorio
    public String getUsername() {
        return this.getUsername();
    }
}
