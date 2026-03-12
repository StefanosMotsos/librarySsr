package cf.library.libraryapp.mapper;

import cf.library.libraryapp.dto.BookEditDTO;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.dto.CategoryReadOnlyDTO;
import cf.library.libraryapp.model.Book;
import cf.library.libraryapp.model.static_data.Category;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Book mapToBookEntity(BookInsertDTO dto) {
        return new Book(null, null, dto.title(), dto.author(), dto.isbn(), dto.publicationYear(), null);
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        return new BookReadOnlyDTO(book.getUuid().toString(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPublicationYear(), book.getCategory().getName());
    }

    public CategoryReadOnlyDTO mapToCategoryReadOnlyDTO(Category category) {
        return new CategoryReadOnlyDTO(category.getId(), category.getName());
    }

    public BookEditDTO mapToBookEditDTO(Book book) {
        return new BookEditDTO(book.getUuid(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPublicationYear(), book.getCategory().getId());
    }

}
