package cf.library.libraryapp.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record BookEditDTO(

        @NotNull
        UUID uuid,

        @NotNull
        @Size(min = 2)
        String title,

        @NotNull
        @Size(min = 2)
        String author,

        @Pattern(regexp = "^(97[89])?\\d{9}[\\dX]$")
        String isbn,

        @NotNull
        @Min(value = 1000)
        @Max(value = 2026)
        Integer publicationYear,

        @NotNull
        Long categoryId
    ){
}
