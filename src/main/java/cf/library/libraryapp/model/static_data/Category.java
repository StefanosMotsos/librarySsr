package cf.library.libraryapp.model.static_data;

import cf.library.libraryapp.model.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    private Set<Book> getAllBooks() {
        return Collections.unmodifiableSet(books);
    }

    public void addBook(Book book) {
        if (books == null) books = new HashSet<>();
        books.add(book);
        book.setCategory(this);
    }

    public void removeBook(Book book) {
        if (books == null) return;
        books.remove(book);
        book.setCategory(null);
    }
}
