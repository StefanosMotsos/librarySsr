package cf.library.libraryapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
        Long categoryId
    ){
}
