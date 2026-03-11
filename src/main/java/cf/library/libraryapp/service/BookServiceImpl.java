package cf.library.libraryapp.service;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.core.exceptions.EntityNotFoundException;
import cf.library.libraryapp.dto.BookEditDTO;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.mapper.Mapper;
import cf.library.libraryapp.model.Book;
import cf.library.libraryapp.model.static_data.Category;
import cf.library.libraryapp.repository.BookRepository;
import cf.library.libraryapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

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

            if (dto.isbn() != null && isBookExists(dto.isbn())) {
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
    @Transactional(readOnly = true)
    public Page<BookReadOnlyDTO> getPaginatedBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        log.debug("Get paginated returned successfully page={} and size={}",
                booksPage.getNumber(), booksPage.getSize());

        return booksPage.map(mapper::mapToBookReadOnlyDTO);
    }

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class, EntityNotFoundException.class})
    public BookReadOnlyDTO updateBook(BookEditDTO dto)
            throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            Book book = bookRepository.findByUuid(dto.uuid())
                    .orElseThrow(() -> new EntityNotFoundException("Book with uuid=" + dto.uuid() + " not found"));

            book.setTitle(dto.title());
            book.setAuthor(dto.author());

            if (!book.getIsbn().equals(dto.isbn())) {
                if (bookRepository.findByIsbn(dto.isbn()).isPresent()) {
                    throw new EntityAlreadyExistsException("Book with isbn=" + dto.isbn() + " already exists");
                }
                book.setIsbn(dto.isbn());
            }

            if (!Objects.equals(dto.categoryId(), book.getCategory().getId())) {
                Category category = categoryRepository.findById(dto.categoryId())
                        .orElseThrow(() -> new EntityInvalidArgumentException("Category id=" + dto.categoryId() + " invalid"));

                Category oldCategory = book.getCategory();
                if (oldCategory != null) oldCategory.removeBook(book);
                category.addBook(book);
            }

            log.info("Book with uuid={} updated successfully", dto.uuid());
            return mapper.mapToBookReadOnlyDTO(book);
        } catch (EntityNotFoundException e) {
            log.error("Update failed for book with uuid={}. Book not found", dto.uuid(), e);
            throw e;
        } catch (EntityAlreadyExistsException e) {
            log.error("Update failed for book with uuid={}. Book with isbn={} already exists", dto.uuid(), dto.isbn(), e);
            throw e;
        } catch (EntityInvalidArgumentException e) {
            log.error("Update failed for teacher with uuid={}. Category id={} invalid", dto.uuid(), dto.categoryId(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookEditDTO getBookByUUID(UUID uuid) throws EntityNotFoundException {
        try {
            Book book = bookRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("Book with uuid=" + uuid + " not found"));
            log.debug("Get book by uuid={} returned successfully", uuid);
            return mapper.mapToBookEditDTO(book);
        } catch (EntityNotFoundException e) {
            log.error("Get book by uuid={} failed", uuid, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBookExists(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

}
