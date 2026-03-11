package cf.library.libraryapp.validator;

import cf.library.libraryapp.core.exceptions.EntityNotFoundException;
import cf.library.libraryapp.dto.BookEditDTO;
import cf.library.libraryapp.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookEditValidator implements Validator {

    private final IBookService bookService;

    @Override
    public boolean supports(Class<?> clazz) {return BookEditDTO.class == clazz;}

    @Override
    public void validate(Object target, Errors errors) {
        BookEditDTO dto = (BookEditDTO) target;

        try {
            BookEditDTO existsInDBBook = bookService.getBookByUUID(dto.uuid());

            if (!existsInDBBook.isbn().equals(dto.isbn())) {
                if (bookService.isBookExists(dto.isbn())) {
                    log.warn("Update failed. Book with isbn={} already exists", dto.isbn());
                    errors.rejectValue("isbn", "isbn.book.exists");
                }
            }

        } catch (EntityNotFoundException e) {
            log.warn("Update failed. Book with uuid={} not found", dto.uuid());
            errors.rejectValue("uuid", "uuid.book.notfound");
        }

    }
}
