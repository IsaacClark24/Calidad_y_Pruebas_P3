package org.isaac.mucino.controller;

import jakarta.validation.Valid;
import org.isaac.mucino.model.LibroModel;
import org.isaac.mucino.service.LibroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/libros")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<LibroModel> obtenerLibro(){
        return libroService.listarLibros();
    }

    @GetMapping("/{id}")
    public LibroModel obtenerLibroPorId(@PathVariable String id){
        return libroService.obtenerLibroPorId(id);
    }

    @PostMapping
    public LibroModel guardarLibro(@Valid @RequestBody LibroModel libroModel){
        return libroService.registrarLibro(libroModel);
    }


}