package com.cursoms.springboot.app.productos.model.dao;

import com.cursoms.springboot.app.productos.model.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto,Long> {
}
