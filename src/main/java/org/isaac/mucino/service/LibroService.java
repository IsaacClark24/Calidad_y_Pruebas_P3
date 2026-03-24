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

        boolean tituloAutorDuplicado = libroRepositorio.obtenerTodos().
                stream().
                anyMatch(libro ->
                        libro.titulo().equalsIgnoreCase(libroModel.titulo())
                                && libro.autor().equals(libroModel.autor())
                );
        if (tituloAutorDuplicado) {
            throw new IllegalStateException("Ya Existe Un Libro Con Ese Titulo Y Autor");
        }

        return libroRepositorio.guardar(libroModel);
    }

}
