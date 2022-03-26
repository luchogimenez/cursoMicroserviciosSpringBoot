package com.cursoms.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccesErrorHandler implements AuthenticationEventPublisher {

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
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String mensaje = "Error Login: "+ exception.getMessage();
        System.out.println(mensaje);
        log.info(mensaje);
    }
}
