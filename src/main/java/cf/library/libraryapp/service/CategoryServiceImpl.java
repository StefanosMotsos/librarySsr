package cf.library.libraryapp.service;

import cf.library.libraryapp.dto.CategoryReadOnlyDTO;
import cf.library.libraryapp.mapper.Mapper;
import cf.library.libraryapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    @Override
    public List<CategoryReadOnlyDTO> findAllCategoriesSortedByName() {
        return categoryRepository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::mapToCategoryReadOnlyDTO)
                .toList();
    }
}
