package es.codeurj.mortez365.advice;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Collection;


@ControllerAdvice
public class BaseControllerAdvice {


    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Obtener el nombre de usuario
            String username = authentication.getName();
            model.addAttribute("username", username);

            // Verificar si el usuario tiene el rol de administrador
            boolean isAdmin = hasRole(authentication, "ROLE_ADMIN");
            model.addAttribute("isAdmin", isAdmin);
        }
    }

    // Método de utilidad para verificar si el usuario tiene un rol específico
    private boolean hasRole(Authentication authentication, String role) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(authority -> authority.getAuthority().equals(role));
    }

}
