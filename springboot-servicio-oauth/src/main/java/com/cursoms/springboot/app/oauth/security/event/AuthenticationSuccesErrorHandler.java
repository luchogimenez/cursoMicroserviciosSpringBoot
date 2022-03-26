package com.cursoms.springboot.app.oauth.security.event;

import com.cursoms.springboot.app.commons.usuarios.models.entity.Usuario;
import com.cursoms.springboot.app.oauth.services.IUsuarioService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccesErrorHandler implements AuthenticationEventPublisher {

    @Autowired
    private IUsuarioService usuarioService;

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccesErrorHandler.class);

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        if(authentication.getDetails() instanceof WebAuthenticationDetails){
            return;
        }
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String mensaje = "Succes Login: "+ userDetails.getUsername();
        System.out.println(mensaje);
        log.info(mensaje);

        Usuario usuario = usuarioService.findByUsername(authentication.getName());

        if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
            usuario.setIntentos(0);
            usuarioService.update(usuario, usuario.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String mensaje = "Error Login: "+ exception.getMessage();
        System.out.println(mensaje);
        log.info(mensaje);

        try {

            StringBuilder errors = new StringBuilder();
            errors.append(mensaje);

            Usuario usuario = usuarioService.findByUsername(authentication.getName());
            if (usuario.getIntentos() == null) {
                usuario.setIntentos(0);
            }
            if(usuario.getEnabled()){
            log.info("Intentos actual es de: " + usuario.getIntentos());

            usuario.setIntentos(usuario.getIntentos()+1);

            log.info("Intentos después es de: " + usuario.getIntentos());

            errors.append(" - Intentos del login: " + usuario.getIntentos());

            if(usuario.getIntentos() >= 3) {
                String errorMaxIntentos = String.format("El usuario %s des-habilitado por máximos intentos.", usuario.getUsername());
                log.error(errorMaxIntentos);
                errors.append(" - " + errorMaxIntentos);
                usuario.setEnabled(false);
            }
            }else {
                String errorMaxIntentos = String.format("El usuario %s des-habilitado por máximos intentos.", usuario.getUsername());
                log.error(errorMaxIntentos);
                errors.append(" - " + errorMaxIntentos);
            }

            usuarioService.update(usuario, usuario.getId());

        } catch (FeignException e) {
            log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
        }
    }
}
