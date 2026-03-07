package cf.library.libraryapp.repository;

import cf.library.libraryapp.model.static_data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByCategoryAsc();
}
