package cf.library.libraryapp.validator;

import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookInsertValidator implements Validator {

    private final IBookService bookService;

    @Override
    public boolean supports(Class<?> clazz) {
        return BookInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookInsertDTO dto = (BookInsertDTO) target;

        if (dto.isbn() != null && bookService.isBookExists(dto.isbn())) {
            log.warn("Book with isbn={} already exists", dto.isbn());
            errors.rejectValue("isbn", "isbn.book.exists");
        }
    }
}
