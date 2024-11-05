package johnoliveira.progetto_settimanale_u5_w2.services;

import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.UnauthorizedException;
import johnoliveira.progetto_settimanale_u5_w2.payloads.DipLoginDTO;
import johnoliveira.progetto_settimanale_u5_w2.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWT jwt;

    public String checkCredentialAndGenerateToken(DipLoginDTO body) {
        // 1. Controllo le credenziali
        // 1.1 Cerco nel DB se esiste un utente con l'email fornita
        Dipendente found = this.dipendenteService.findByEmail(body.email());
        // 1.2 Verifico che la password di quell'utente corrisponda a quella fornita

        if (found.getPassword().equals(body.password())) {
            // 2. Se sono OK --> Genero il token
            String accessToken = jwt.createToken(found);
            // 3. Ritorno il token
            return accessToken;
        } else {
            // 4. Se le credenziali sono errate --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
