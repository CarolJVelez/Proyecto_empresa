package com.sg.gestion.repositories;

import com.sg.gestion.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    @Query("Select p From Producto p "
            +"where (:id is null or p.id = :id )"
            +"and (:nombre is null or p.nombre = :nombre )"
            +"and (:cantidadBodega is null or p.cantidadBodega = :cantidadBodega )"
            +"and (:descripcion is null or p.descripcion = :descripcion )"
            +"and (:modelo is null or p.modelo = :modelo )"
            +"and (:valorVenta is null or p.valorVenta = :valorVenta )"
    )
    List<Producto> listarProductosBy(
      @Param("id") Long id,
      @Param("nombre") String nombre,
      @Param("cantidadBodega") Integer cantidadBodega,
      @Param("descripcion") String descripcion,
      @Param("modelo") String modelo,
      @Param("valorVenta") Integer valorVenta
      );

    @Query("Select p From Producto p "
            +"where (:id is null or p.id = :id )"
    )
    List<Producto> listarProductosById(
            @Param("id") Long id
    );

    @Query("SELECT p FROM Producto p WHERE (:nombre IS NOT NULL AND p.nombre = :nombre)")

    List<Producto> listarProductosByNombre(
            @Param("nombre") String nombre
    );
}
