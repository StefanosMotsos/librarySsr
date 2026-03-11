package cf.library.libraryapp.service;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {

    BookReadOnlyDTO saveBook(BookInsertDTO bookInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    boolean isBookExists(String isbn);
    Page<BookReadOnlyDTO> getPaginatedTeachers(Pageable pageable);
}
