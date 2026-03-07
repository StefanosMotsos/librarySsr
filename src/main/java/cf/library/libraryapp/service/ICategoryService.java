package cf.library.libraryapp.service;

import cf.library.libraryapp.dto.CategoryReadOnlyDTO;

import java.util.List;

public interface ICategoryService {

    List<CategoryReadOnlyDTO> findAllCategoriesSortedByName();
}
