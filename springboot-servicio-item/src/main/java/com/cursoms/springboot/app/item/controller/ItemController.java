package com.cursoms.springboot.app.item.controller;

import com.cursoms.springboot.app.commons.models.entity.Producto;
import com.cursoms.springboot.app.item.models.Item;
import com.cursoms.springboot.app.item.models.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

    @Value("${configuracion.texto}")
    private String texto;

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("serviceFeign")
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

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto){
        logger.info(texto);

        Map<String,String> json = new HashMap<>();
        json.put("texto",texto);
        json.put("puerto",puerto);

        if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.nombre",env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email",env.getProperty("configuracion.autor.email"));

        }

        return new ResponseEntity<Map<String,String>> (json, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){

        return itemService.save(producto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id){

        return itemService.update(producto,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimiar(@PathVariable Long id){

        itemService.delete(id);
    }
}
