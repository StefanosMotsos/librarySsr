package cf.library.libraryapp.service;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;

public interface IBookService {

    BookReadOnlyDTO saveBook(BookInsertDTO bookInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    boolean isBookExists(String isbn);
}
