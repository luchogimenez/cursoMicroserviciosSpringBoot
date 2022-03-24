package com.cursoms.springboot.app.item.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cursoms.springboot.app.commons.models.entity.Producto;

import java.util.List;

@FeignClient(name="servicio-productos")
public interface ProductoClienteRest {

    @GetMapping("/")
    public List<Producto> listar();

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id);

    @PostMapping
    public Producto crear(@RequestBody Producto producto);

    @PutMapping("/{id}")
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public void elimiar(@PathVariable Long id);
}
