package com.cursoms.springboot.app.item.controller;

import com.cursoms.springboot.app.item.models.Item;
import com.cursoms.springboot.app.item.models.Producto;
import com.cursoms.springboot.app.item.models.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;

    @GetMapping("/")
    public List<Item> listar(@RequestParam(name="nombre", required = false) String nombre, @RequestHeader(name="token-request", required = false) String token){
        System.out.println(nombre);
        System.out.println(token);
        return itemService.findAll();
    }

    /* @GetMapping("/{id}/{cantidad}")
    public Item getItem(@PathVariable Long id,@PathVariable Integer cantidad){
        return cbFactory.create("items")
                .run(()->itemService.findById(id,cantidad),e -> metodoAlternativo(id,cantidad,e));
    }*/

    @CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/{id}/{cantidad}")
    public Item getItem(@PathVariable Long id,@PathVariable Integer cantidad){
        return itemService.findById(id,cantidad);
    }

    private Item metodoAlternativo(Long id, Integer cantidad,Throwable e) {
        logger.info(e.getMessage());
        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Producto alternativo");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return item;
    }

}
