package com.sg.gestion.services;

import com.sg.gestion.dto.ProductoDTO;
import com.sg.gestion.entities.Producto;
import com.sg.gestion.repositories.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService  {

    @Autowired
    private ProductoRepositorio productoRepositorio;
    public Producto crearProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setCantidadBodega(productoDTO.getCantidadBodega());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setModelo(productoDTO.getModelo());
        producto.setValorVenta(productoDTO.getValorVenta());
        return productoRepositorio.save(producto);
    }


    public List<ProductoDTO> listarProductos(){
        List<Producto> productos = productoRepositorio.findAll();
        return productos.stream()
                .map(producto -> {
                    ProductoDTO productoDTO = convertirProductoADTO(producto);
                    productoDTO.setId(producto.getId());
                    return productoDTO;
                })
                        .collect(Collectors.toList());
    }

    private ProductoDTO convertirProductoADTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCantidadBodega(producto.getCantidadBodega());
        productoDTO.setModelo(producto.getModelo());
        productoDTO.setValorVenta(producto.getValorVenta());
        return productoDTO;
    }

    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }

    public Producto actualizarProducto(Long id, ProductoDTO productoDTO) {
        // Verificar si el producto con el ID dado existe en la base de datos
        Producto productoExistente = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Actualizar los campos del producto existente con los nuevos valores proporcionados en el DTO
        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setCantidadBodega(productoDTO.getCantidadBodega());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setModelo(productoDTO.getModelo());
        productoExistente.setValorVenta(productoDTO.getValorVenta());

        // Guardar y devolver el producto actualizado
        return productoRepositorio.save(productoExistente);
    }
}
