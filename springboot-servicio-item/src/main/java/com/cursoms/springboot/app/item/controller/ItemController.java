package com.cursoms.springboot.app.item.controller;

import com.cursoms.springboot.app.item.models.Item;
import com.cursoms.springboot.app.item.models.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;

    @GetMapping("/")
    public List<Item> listar(@RequestParam(name="nombre", required = false) String nombre, @RequestHeader(name="token-request", required = false) String token){
        System.out.println(nombre);
        System.out.println(token);
        return itemService.findAll();
    }

    @GetMapping("/{id}/{cantidad}")
    public Item getItem(@PathVariable Long id,@PathVariable Integer cantidad){
        return itemService.findById(id,cantidad);
    }

}
