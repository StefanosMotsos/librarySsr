package cf.library.libraryapp.dto;

public record BookReadOnlyDTO(
        String uuid, String title, String author, String isbn, Integer publicationYear, String category) {
}
