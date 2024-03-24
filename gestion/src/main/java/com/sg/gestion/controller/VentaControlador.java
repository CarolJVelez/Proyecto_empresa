package com.sg.gestion.controller;

import com.sg.gestion.dto.VentaDTO;
import com.sg.gestion.entities.Venta;
import com.sg.gestion.exceptions.PrecondicionFallidaException;
import com.sg.gestion.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VentaControlador {

    @Autowired
    private VentaService ventaService;


    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @PostMapping("/ventas")
    public void registrarVenta(@RequestBody VentaDTO ventadTO) throws PrecondicionFallidaException {
        ventaService.crearVenta(ventadTO);
    }
}
