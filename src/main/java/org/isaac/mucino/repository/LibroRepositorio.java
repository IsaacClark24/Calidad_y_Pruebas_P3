package org.isaac.mucino.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.isaac.mucino.model.LibroModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LibroRepositorio {

    private static final String COLLECTION = "libros";

    private final Firestore firestore;

    public LibroRepositorio(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<LibroModel> obtenerTodos(){

        try{
            ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION).get();
            QuerySnapshot querySnapshot = future.get();
            List<LibroModel> libros = new ArrayList<>();

            for(DocumentSnapshot document: querySnapshot.getDocuments()){
                LibroModel libro = document.toObject(LibroModel.class);

                if(libro != null){
                    libros.add(new LibroModel(
                            document.getId(),
                            libro.titulo(),
                            libro.autor(),
                            libro.anio()
                    ));
                }else{
                    System.out.println("No Se Encontro Ningun El Libro");
                }
            }
            return libros;
        } catch (Exception e) {
            throw new RuntimeException("No Fue Posible Obtener Los Libros Por El Error"+ e);
        }
    }

    public Optional<LibroModel> obtenerPorId(String id){
        if(id == null || id.isBlank())
            return Optional.empty();
        try {
            DocumentReference documentReference = firestore.collection(COLLECTION).document(id);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();

            if(document.exists()){
                LibroModel libro = document.toObject(LibroModel.class);

                if(libro != null){
                    return Optional.of(new LibroModel(
                            document.getId(),
                            libro.titulo(),
                            libro.autor(),
                            libro.anio()
                    ));
                }else{
                    System.out.println("No Se Encontro Ningun El Libro Con El Id " + id);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("No Fue Posible Obtener El Libro Con El Id " + id + " Por El Error "+e);
        }
    }

    public LibroModel guardar(LibroModel libro){
        try{
            CollectionReference collectionReference = firestore.collection(COLLECTION);
            DocumentReference documentReference = collectionReference.document();
            ApiFuture<WriteResult> future = documentReference.set(Map.of(
                    "titulo", libro.titulo(),
                    "autor", libro.autor(),
                    "anio", libro.anio()
            ));

            future.get();

            return new LibroModel(
                    documentReference.getId(),
                    libro.titulo(),
                    libro.autor(),
                    libro.anio()
            );

        } catch (Exception e) {
            throw new RuntimeException("No Fue Posible Guardar El Libro Por El Error" + e);
        }
    }
}
