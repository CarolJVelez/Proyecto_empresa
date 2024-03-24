package com.sg.gestion.services;

import com.sg.gestion.controller.ProductoControlador;
import com.sg.gestion.dto.DetalleVentaDTO;
import com.sg.gestion.dto.ProductoDTO;
import com.sg.gestion.dto.VentaDTO;
import com.sg.gestion.entities.DetalleVenta;
import com.sg.gestion.entities.Venta;
import com.sg.gestion.exceptions.PrecondicionFallidaException;
import com.sg.gestion.repositories.DetalleVentaRepositorio;
import com.sg.gestion.repositories.ProductoRepositorio;
import com.sg.gestion.repositories.VentaRepositorio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class VentaService implements UserDetailsService {
    private  VentaRepositorio ventaRepositorio;
    private  DetalleVentaRepositorio detalleVentaRepositorio;
    private final ProductoService productoService;

    @Autowired
    public VentaService(VentaRepositorio ventaRepositorio, DetalleVentaRepositorio detalleVentaRepositorio, ProductoService productoService) {
        this.ventaRepositorio = ventaRepositorio;
        this.detalleVentaRepositorio = detalleVentaRepositorio;

        this.productoService = productoService;
    }

    public Venta crearVenta(VentaDTO ventaDTO) throws PrecondicionFallidaException {

        Venta venta = new Venta();
        venta.setIdUsuario(ventaDTO.getIdUsuario());
        venta.setFechaVenta(ventaDTO.getFechaVenta());
        Venta ventaGuardada = ventaRepositorio.save(venta);

        // Luego, guardas los detalles de venta asociados a esta venta
        List<DetalleVentaDTO> detallesDTO = ventaDTO.getDetalles();
        for (DetalleVentaDTO detalleDTO : detallesDTO) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta((venta));
            List<ProductoDTO> infor = productoService.listarProductosById(detalleDTO.getIdProducto());
            if (infor.get(0).getCantidadBodega() < detalleDTO.getCantidad() || detalleDTO.getCantidad() == 0)
            {
                throw new PrecondicionFallidaException("No hay suficiente cantidad en bodega para vender");
            }
            Integer resultaCantidadBodega = infor.get(0).getCantidadBodega() - detalleDTO.getCantidad();
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setNombre(infor.get(0).getNombre());
            productoDTO.setCantidadBodega(resultaCantidadBodega);
            productoDTO.setDescripcion(infor.get(0).getDescripcion());
            productoDTO.setModelo(infor.get(0).getModelo());
            productoDTO.setValorVenta(infor.get(0).getValorVenta());

            productoService.actualizarProducto(infor.get(0).getId(), productoDTO);
            detalle.setIdProducto(detalleDTO.getIdProducto());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalleVentaRepositorio.save(detalle);
        }

        return ventaGuardada;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}