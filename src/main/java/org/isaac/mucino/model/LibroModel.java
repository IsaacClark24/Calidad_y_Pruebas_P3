package org.isaac.mucino.model;

import jakarta.validation.constraints.*;

public record LibroModel(
        String id,
        @NotBlank(message = "El Titulo No Puede Estar Vacio")
        @Size(max = 60, message = "El Titulo No Puede Superar Los 60 Caracteres")
        @Pattern(regexp = "^[\\p{L} \\-.&@,+]+$", message = "El Titulo No Puede Llevar Caracteres No Validos")
        String titulo,
        @NotBlank(message = "El Autor No Puede Estar Vacio")
        @Size(max = 60, message = "El Autor No Puede Superar Los 60 Caracteres")
        @Pattern(regexp = "^[\\p{L} \\-.&,]+$", message = "El Nombre Del Autor(es) No Puede Llevar Caracteres No Validos")
        String autor,
        @Min(value = 1, message = "La edición mínima es 1")
        @Max(value = 20, message = "La edición máxima es 20")
        int edicion,
        @NotBlank(message = "La editorial no puede estar vacía")
        @Pattern(regexp = "^[\\p{L}\\s.&]+$", message = "La editorial solo acepta letras, espacios, puntos y '&'")
        String editorial,
        @Min(value = 1899, message = "El Año No Puede Ser Menor A 1990")
        @Max(value = 2027, message = "El Año No Puede Ser Mayor A 2026")
        int anio
) {
    public LibroModel {
        titulo = titulo == null ? null : titulo.trim();
        autor = autor == null ? null : autor.trim();
        editorial = (editorial == null) ? null : editorial.trim();
    }
}