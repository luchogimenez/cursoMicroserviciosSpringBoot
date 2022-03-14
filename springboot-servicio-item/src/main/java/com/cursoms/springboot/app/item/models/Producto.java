package com.cursoms.springboot.app.item.models;

import lombok.Data;

import java.util.Date;

@Data
public class Producto {

    private Long Id;
    private String nombre;
    private Double precio;
    private Date createAt;
    private Integer port;

}
