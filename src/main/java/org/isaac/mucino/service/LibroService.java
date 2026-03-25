package org.isaac.mucino.service;

import org.isaac.mucino.model.LibroModel;
import org.isaac.mucino.repository.LibroRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    private final LibroRepositorio libroRepositorio;

    public LibroService(LibroRepositorio libroRepositorio) {
        this.libroRepositorio = libroRepositorio;
    }

    public List<LibroModel> listarLibros() {
        return libroRepositorio.obtenerTodos();
    }

    public LibroModel obtenerLibroPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El Id No Es Valido");
        }
        return libroRepositorio.obtenerPorId(id).orElseThrow(() ->
                new IllegalArgumentException("El Id" + id + " No Existe"));
    }

    public LibroModel registrarLibro(LibroModel libroModel) {
        if (libroModel == null) {
            throw new IllegalArgumentException(
                    "El Campo Libro No Puede Ser Nulo");
        }
        return libroRepositorio.guardar(libroModel);
    }

    public List<LibroModel> obtenerLibrosPorTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título de búsqueda no puede estar vacío");
        }
        return libroRepositorio.obtenerLibroPorTitulo(titulo);
    }

    public List<LibroModel> obtenerLibrosPorAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El nombre del autor no puede estar vacío");
        }
        return libroRepositorio.obtenerLibroPorAutor(autor);
    }
}
