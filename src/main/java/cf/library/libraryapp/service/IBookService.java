package cf.library.libraryapp.service;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.core.exceptions.EntityNotFoundException;
import cf.library.libraryapp.dto.BookEditDTO;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IBookService {

    BookReadOnlyDTO saveBook(BookInsertDTO bookInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    boolean isBookExists(String isbn);
    Page<BookReadOnlyDTO> getPaginatedBooks(Pageable pageable);

    BookReadOnlyDTO updateBook(BookEditDTO bookEditDTO)
            throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException;

    BookEditDTO getBookByUUID(UUID uuid) throws EntityNotFoundException;
}
