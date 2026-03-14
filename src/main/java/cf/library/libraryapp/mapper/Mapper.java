package cf.library.libraryapp.mapper;

import cf.library.libraryapp.dto.*;
import cf.library.libraryapp.model.Book;
import cf.library.libraryapp.model.Role;
import cf.library.libraryapp.model.User;
import cf.library.libraryapp.model.static_data.Category;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Book mapToBookEntity(BookInsertDTO dto) {
        return new Book(null, null, dto.title(), dto.author(), dto.isbn(), dto.publicationYear(), null);
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        return new BookReadOnlyDTO(book.getUuid().toString(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPublicationYear(), book.getCategory().getName());
    }

    public CategoryReadOnlyDTO mapToCategoryReadOnlyDTO(Category category) {
        return new CategoryReadOnlyDTO(category.getId(), category.getName());
    }

    public BookEditDTO mapToBookEditDTO(Book book) {
        return new BookEditDTO(book.getUuid(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getPublicationYear(), book.getCategory().getId());
    }

    //Security related Entity-DTO Mapping:

    public User mapToUserEntity(UserInsertDTO userInsertDTO) {
        return new User(userInsertDTO.username(), userInsertDTO.password());
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getUuid().toString(), user.getUsername(), user.getRole().getName());
    }

    public RoleReadOnlyDTO mapToRoleReadOnlyDTO(Role role) {
        return new RoleReadOnlyDTO(role.getId(), role.getName());
    }
}
