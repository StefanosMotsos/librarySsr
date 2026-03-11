package cf.library.libraryapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BookInsertDTO(

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

    public static BookInsertDTO empty() {
        return new BookInsertDTO("", "", "", 0L);
    }
}
