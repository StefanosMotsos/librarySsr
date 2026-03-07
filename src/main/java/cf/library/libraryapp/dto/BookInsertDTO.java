package cf.library.libraryapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BookInsertDTO(

        @NotNull(message = "no null name")
        @Size(min = 2, message = "at least 2 chars")
        String title,

        @NotNull(message = "no null author")
        @Size(min = 2, message = "at least 2 chars")
        String authorName,

        @Pattern(regexp = "^(97[89])?\\d{9}[\\dX]$", message = "isbn not valid")
        String isbn,

        //9780306406157 9783161484100 9780140449136

        @NotNull(message = "no null category")
        Long categoryId
    ){

    public static BookInsertDTO empty() {
        return new BookInsertDTO("", "", "", 0L);
    }
}
