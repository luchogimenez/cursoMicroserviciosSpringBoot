package com.cursoms.springboot.app.oauth.services;

import com.cursoms.springboot.app.commons.usuarios.models.entity.Usuario;
import com.cursoms.springboot.app.oauth.clients.UsuarioFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioFeignClient client;

    private Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = client.findByUsername(username);
        if(usuario==null){
            log.error("Error de login, no existe el usuario '"+usuario+"' en el sistema");
            throw new UsernameNotFoundException("Error de login, no existe el usuario '"+usuario+"' en el sistema");
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(simpleGrantedAuthority -> log.info("Role: "+simpleGrantedAuthority.getAuthority()))
                .collect(Collectors.toList());
        log.info("Usuario autenticado: "+ username);
        return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(),true,true,true,authorities);
    }
}
