package com.cursoms.springboot.app.item.models.service;

import com.cursoms.springboot.app.item.models.Item;
import com.cursoms.springboot.app.commons.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private RestTemplate clienteRest;

    @Override
    public List<Item> findAll() {
        List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://servicio-productos/",Producto[].class));

        return productos.stream().map(producto -> new Item(producto,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String,String> pathVariable = new HashMap<String,String>();
        pathVariable.put("id",id.toString());
        Producto producto = clienteRest.getForObject("http://servicio-productos/{id}",Producto.class,pathVariable);
        return new Item(producto,cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
        ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/",HttpMethod.POST,body,Producto.class );
        return response.getBody();
    }

    @Override
    public Producto update(Producto producto, Long id) {
        Map<String,String> pathVariable = new HashMap<String,String>();
        pathVariable.put("id",id.toString());
        HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
        ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/{id}",HttpMethod.PUT,body,Producto.class,pathVariable );
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String,String> pathVariable = new HashMap<String,String>();
        pathVariable.put("id",id.toString());
        clienteRest.delete("http://servicio-productos/{id}",pathVariable);
    }
}
