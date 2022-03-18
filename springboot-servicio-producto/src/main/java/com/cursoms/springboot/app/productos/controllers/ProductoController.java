package com.cursoms.springboot.app.productos.controllers;

import com.cursoms.springboot.app.productos.model.entity.Producto;
import com.cursoms.springboot.app.productos.model.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    //@Value("${server.port}")
    //private Integer port;

    @Autowired
    private Environment env;

    @GetMapping("/")
    public List<Producto> listar(){

        return productoService.findAll().stream().map(producto -> {
            //producto.setPort(port);
            producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id) throws InterruptedException{
        if(id.equals(10L)){
            throw new IllegalStateException("Producto no encontrado!");
        }
        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }
        Producto producto = productoService.findById(id);
         //producto.setPort(port);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        return producto;
    }
}
