package com.cursoms.springboot.app.item.clientes;

import com.cursoms.springboot.app.item.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="servicio-productos",url = "localhost:8001")
public interface ProductoClienteRest {

    @GetMapping("/")
    public List<Producto> listar();

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id);
}
