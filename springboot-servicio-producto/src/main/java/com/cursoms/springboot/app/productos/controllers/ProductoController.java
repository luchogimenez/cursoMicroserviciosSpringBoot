package com.cursoms.springboot.app.productos.controllers;

import com.cursoms.springboot.app.commons.models.entity.Producto;
import com.cursoms.springboot.app.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){

        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto prodcuto, @PathVariable Long id){
        Producto productoDb = productoService.findById(id);

        productoDb.setNombre(prodcuto.getNombre());
        productoDb.setPrecio(prodcuto.getPrecio());

        return productoService.save(productoDb);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimiar(@PathVariable Long id){

        productoService.deleteById(id);
    }
}
