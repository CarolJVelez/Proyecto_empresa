package com.sg.gestion.services;

import com.sg.gestion.dto.ProductoDTO;
import com.sg.gestion.entities.Producto;
import com.sg.gestion.entities.Usuario;
import com.sg.gestion.exceptions.PrecondicionFallidaException;
import com.sg.gestion.repositories.ProductoRepositorio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
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

    public List<ProductoDTO> listarProductosBy(Long id, String nombre, Integer cantidadBodega, String descripcion,
                                               String modelo, Integer valorVenta){
        List<Producto> productos = productoRepositorio.listarProductosBy(id, nombre, cantidadBodega, descripcion,
                modelo, valorVenta);
        return productos.stream()
                .map(producto -> {
                    ProductoDTO productoDTO = convertirProductoADTO(producto);
                    productoDTO.setId(producto.getId());
                    return productoDTO;
                })
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarProductosById(Long id){
        List<Producto> productos = productoRepositorio.listarProductosById(id);
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
    public List<ProductoDTO> listarProductosByNombre(String nombre){
        List<Producto> productos = productoRepositorio.listarProductosByNombre(nombre);
        if (productos.isEmpty()) {
            throw new PrecondicionFallidaException("No existe el producto");
        }
        return productos.stream()
                .map(producto -> {
                    ProductoDTO productoDTO = convertirProductoADTO(producto);
                    productoDTO.setId(producto.getId());

                    return productoDTO;
                })
                .collect(Collectors.toList());
    }
}
