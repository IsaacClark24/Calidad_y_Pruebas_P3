package org.isaac.mucino.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.isaac.mucino.model.LibroModel;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LibroRepositorio {

    private static final String COLLECTION = "libros";

    private final Firestore firestore;

    public LibroRepositorio(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<LibroModel> obtenerTodos(){

        try {
            ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION).get();
            QuerySnapshot querySnapshot = future.get();
            List<LibroModel> libros = new ArrayList<>();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                // Mapeo manual: extraemos cada campo y lo pasamos al constructor del Record
                libros.add(new LibroModel(
                        document.getId(),
                        document.getString("titulo"),
                        document.getString("autor"),
                        document.getLong("edicion") != null ? Objects.requireNonNull(document.getLong("edicion")).intValue() : 0,
                        document.getString("editorial"),
                        document.getLong("anio") != null ? Objects.requireNonNull(document.getLong("anio")).intValue() : 0
                ));
            }
            return libros;
        } catch (Exception e) {
            throw new RuntimeException("No Fue Posible Obtener Los Libros Por El Error: " + e.getMessage());
        }
    }

    public Optional<LibroModel> obtenerPorId(String id){
        if(id == null || id.isBlank())
            return Optional.empty();
        try {
            DocumentReference documentReference = firestore.collection(COLLECTION).document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                // Retornamos el LibroModel construido manualmente con los datos de Firebase
                return Optional.of(new LibroModel(
                        document.getId(),
                        document.getString("titulo"),
                        document.getString("autor"),
                        document.getLong("edicion") != null ? Objects.requireNonNull(document.getLong("edicion")).intValue() : 0,
                        document.getString("editorial"),
                        document.getLong("anio") != null ? Objects.requireNonNull(document.getLong("anio")).intValue() : 0
                ));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("No Fue Posible Obtener El Libro Con El Id " + id + " Por El Error: " + e.getMessage());
        }
    }

    public LibroModel guardar(LibroModel libro){
        try{
            CollectionReference collectionReference = firestore.collection(COLLECTION);
            DocumentReference documentReference = collectionReference.document();
            ApiFuture<WriteResult> future = documentReference.set(Map.of(
                    "titulo", libro.titulo(),
                    "autor", libro.autor(),
                    "edicion", libro.edicion(),
                    "editorial", libro.editorial(),
                    "anio", libro.anio()
            ));

            future.get();

            return new LibroModel(
                    documentReference.getId(),
                    libro.titulo(),
                    libro.autor(),
                    libro.edicion(),
                    libro.editorial(),
                    libro.anio()
            );

        } catch (Exception e) {
            throw new RuntimeException("No Fue Posible Guardar El Libro Por El Error" + e);
        }
    }
}
