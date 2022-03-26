package com.cursoms.springboot.app.oauth.services;

import com.cursoms.springboot.app.commons.usuarios.models.entity.Usuario;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUsuarioService {

    public Usuario findByUsername(String username);
    public Usuario update(Usuario usuario,Long id);
}
