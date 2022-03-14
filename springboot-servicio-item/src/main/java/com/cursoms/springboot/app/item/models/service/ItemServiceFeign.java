package com.cursoms.springboot.app.item.models.service;

import com.cursoms.springboot.app.item.clientes.ProductoClienteRest;
import com.cursoms.springboot.app.item.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductoClienteRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return clienteFeign.listar().stream().map(producto -> new Item(producto,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(clienteFeign.getById(id),cantidad);
    }
}
