package cf.library.libraryapp.mapper;

import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.model.Book;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Book mapToBookEntity(BookInsertDTO dto) {
        return new Book(null, null, dto.title(), dto.authorName(), dto.isbn(), null);
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        return new BookReadOnlyDTO(book.getUuid().toString(), book.getTitle(), book.getAuthor());
    }

}
