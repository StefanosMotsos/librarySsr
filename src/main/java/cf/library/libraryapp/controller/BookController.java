package cf.library.libraryapp.controller;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.dto.CategoryReadOnlyDTO;
import cf.library.libraryapp.service.IBookService;
import cf.library.libraryapp.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;
    private final ICategoryService categoryService;

    @GetMapping("/insert")
    public String getBookForm(Model model) {
        model.addAttribute("bookInsertDTO", BookInsertDTO.empty());
        return "book-insert";
    }

    @PostMapping("/insert")
    public String bookInsert(@Valid @ModelAttribute("bookInsertDTO") BookInsertDTO bookInsertDTO,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book-insert";
        }

        try {

            BookReadOnlyDTO bookReadOnlyDTO = bookService.saveBook(bookInsertDTO);
            redirectAttributes.addFlashAttribute("bookReadOnlyDTO", bookReadOnlyDTO);

            return "redirect:/books/success";

        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "book-insert";
        }
    }

    @GetMapping("/success")
    public String bookSuccess(Model model) {
        return "book-success";
    }

    @ModelAttribute("categoriesReadOnlyDTO")
    public List<CategoryReadOnlyDTO> categories() {
        return categoryService.findAllCategoriesSortedByName();
    }
}
