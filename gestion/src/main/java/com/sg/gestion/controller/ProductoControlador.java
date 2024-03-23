package com.sg.gestion.controller;

import com.sg.gestion.dto.ProductoDTO;
import com.sg.gestion.entities.Producto;
import com.sg.gestion.services.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoControlador {

    private final ProductoService productoService;

    public ProductoControlador(ProductoService productoService) {
        this.productoService = productoService;
    }
    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @PostMapping("/productos")
    public Producto crearproducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @GetMapping("/productos")
    public List<ProductoDTO> listarProductos(){
        return productoService.listarProductos();
    }

    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @DeleteMapping("/productos/{id}")
    public void eliminarProductos (@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @PutMapping("/productos/{id}")
    public Producto actualizarProducto (@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.actualizarProducto(id, productoDTO);
    }
}
