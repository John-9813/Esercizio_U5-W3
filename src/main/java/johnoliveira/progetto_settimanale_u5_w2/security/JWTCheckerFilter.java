package johnoliveira.progetto_settimanale_u5_w2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import johnoliveira.progetto_settimanale_u5_w2.entities.Dipendente;
import johnoliveira.progetto_settimanale_u5_w2.exceptions.UnauthorizedException;
import johnoliveira.progetto_settimanale_u5_w2.services.DipendenteService;
import johnoliveira.progetto_settimanale_u5_w2.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // altrimenti questa classe non verrà utilizzata nella catena dei filtri
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;

    @Autowired
    private DipendenteService dipendenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Verifichiamo se nella richiesta è presente l'Authorization Header, e se è ben formato ("Bearer josdjojosdj...") se non c'è oppure
        // se non ha il formato giusto --> 401
        String authHeader = request.getHeader("Authorization");
        // "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MzA3MTk2MTcsImV4cCI6MTczMTMyNDQxNywic3ViIjoiM2RlMDRlYmEtNDJjOC00YzE4LWFhNzUtNzY3MDAwZWVhYmMxIn0.HsVC06J2LXg1-lrWb5ZcenLfm0Wd6zEOCE9-FPTDQrQ"
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");

        // 2. Estraiamo il token dall'header
        String accessToken = authHeader.substring(7);

        // 3. Verifichiamo se il token è stato manipolato (verifichiamo la signature) o se è scaduto (verifichiamo Expiration Date)
        jwt.verifyToken(accessToken);

        // ******** AUTORIZZAZIONE************
        // 1. Cerco l'utente tramite id (l'id l'abbiamo messo nel token!)
        String dipendenteId = jwt.getIdFromToken(accessToken);
        Dipendente currentDipendente = this.dipendenteService.findById(Long.valueOf(dipendenteId));
        // 2. Trovato l'utente posso associarlo al cosiddetto Security Context
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentDipendente, null, currentDipendente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication); // si aggiorna il SecurityContext associandogli l'utente autenticato

        // 4. Se tutto è OK, andiamo avanti (passiamo la richiesta al prossimo filtro o al controller)
        filterChain.doFilter(request, response);

        // 5. Se qualcosa non va con il token --> 401
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
