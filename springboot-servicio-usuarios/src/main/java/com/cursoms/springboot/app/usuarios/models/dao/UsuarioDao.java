package com.cursoms.springboot.app.usuarios.models.dao;

import com.cursoms.springboot.app.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario,Long> {

    public Usuario findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.username=?1")
    public Usuario obtenerPorUsername(String username);
}
