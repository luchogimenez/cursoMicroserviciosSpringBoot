package com.cursoms.springboot.app.productos.model.service;

import com.cursoms.springboot.app.productos.model.entity.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> findAll();
    public Producto findById(Long id);
}
