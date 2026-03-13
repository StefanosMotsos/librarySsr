package cf.library.libraryapp.repository;

import cf.library.libraryapp.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByUuid(UUID uuid);

    @EntityGraph(attributePaths = {"category"})
    Page<Book> findAllByDeletedFalse(Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCaseAndDeletedFalse(String title, Pageable pageable);

    Optional<Book> findByIsbnAndDeletedFalse(String isbn);
    Optional<Book> findByUuidAndDeletedFalse(UUID uuid);
}
