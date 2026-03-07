package cf.library.libraryapp.service;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.mapper.Mapper;
import cf.library.libraryapp.model.Book;
import cf.library.libraryapp.model.static_data.Category;
import cf.library.libraryapp.repository.BookRepository;
import cf.library.libraryapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements IBookService{

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public BookReadOnlyDTO saveBook(BookInsertDTO dto)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {

            if (dto.isbn() != null && bookRepository.findByIsbn(dto.isbn()).isPresent()) {
                throw new EntityAlreadyExistsException("Book with isbn= " + dto.isbn() + " already exists");
            }

            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() ->
                            new EntityInvalidArgumentException("Category id= " + dto.categoryId() + " invalid"));

            Book book = mapper.mapToBookEntity(dto);
            category.addBook(book);
            bookRepository.save(book);

            log.info("book with isbn={} saved successfully", dto.isbn());

            return mapper.mapToBookReadOnlyDTO(book);

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for book with isbn={}. Book already exists.", dto.isbn());
            throw e;
        } catch (EntityInvalidArgumentException e) {
            log.error("Saved failed for book with isbn={}. Category={} invalid", dto.isbn(), dto.categoryId());
            throw e;
        }
    }

    @Override
    public boolean isBookExists(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

}
