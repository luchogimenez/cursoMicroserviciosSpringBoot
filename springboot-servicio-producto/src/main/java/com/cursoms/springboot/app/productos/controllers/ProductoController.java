package com.cursoms.springboot.app.productos.controllers;

import com.cursoms.springboot.app.productos.model.entity.Producto;
import com.cursoms.springboot.app.productos.model.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/")
    public List<Producto> listar(){

        return productoService.findAll().stream().map(producto -> {
            producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id){
        Producto producto = productoService.findById(id);
         producto.setPort(port);
        return producto;
    }
}
