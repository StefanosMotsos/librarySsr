package cf.library.libraryapp.controller;

import cf.library.libraryapp.core.exceptions.EntityAlreadyExistsException;
import cf.library.libraryapp.core.exceptions.EntityInvalidArgumentException;
import cf.library.libraryapp.core.exceptions.EntityNotFoundException;
import cf.library.libraryapp.dto.BookEditDTO;
import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.dto.CategoryReadOnlyDTO;
import cf.library.libraryapp.service.IBookService;
import cf.library.libraryapp.service.ICategoryService;
import cf.library.libraryapp.validator.BookEditValidator;
import cf.library.libraryapp.validator.BookInsertValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;
    private final ICategoryService categoryService;
    private final BookInsertValidator bookInsertValidator;
    private final BookEditValidator bookEditValidator;

    @GetMapping("/insert")
    public String getBookForm(Model model) {
        model.addAttribute("bookInsertDTO", BookInsertDTO.empty());
        return "book-insert";
    }

    @PostMapping("/insert")
    public String bookInsert(@Valid @ModelAttribute("bookInsertDTO") BookInsertDTO bookInsertDTO,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {

        bookInsertValidator.validate(bookInsertDTO, bindingResult);

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

    @GetMapping({ "", "/"})
    public String getPaginatedBooks(@PageableDefault(size = 5, sort = "author")Pageable pageable,
                                       Model model) {

        Page<BookReadOnlyDTO> booksPage = bookService.getPaginatedBooksDeletedFalse(pageable);

        model.addAttribute("books", booksPage.getContent());
        model.addAttribute("page", booksPage);

        return "books";
    }

    @GetMapping("/edit/{uuid}")
    public String getBookEdit(@PathVariable UUID uuid, Model model) {

        try {
            BookEditDTO bookEditDTO = bookService.getBookByUUID(uuid);
            model.addAttribute("bookEditDTO", bookEditDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "book-edit";
    }

    @PostMapping("/edit")
    public String updateBook(@Valid @ModelAttribute("bookEditDTO") BookEditDTO bookEditDTO,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        bookEditValidator.validate(bookEditDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book-edit";
        }

        try {
            BookReadOnlyDTO bookReadOnlyDTO = bookService.updateBook(bookEditDTO);
            redirectAttributes.addFlashAttribute("bookReadOnlyDTO", bookReadOnlyDTO);
            return "redirect:/books/update-success";
        } catch (EntityNotFoundException | EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "teacher-edit";
        }

    }

    @PostMapping("/delete/{uuid}")
    public String deleteBook(@PathVariable UUID uuid, Model model, RedirectAttributes redirectAttributes) {

        try {
            BookReadOnlyDTO dto = bookService.deleteBookByUUID(uuid);
            redirectAttributes.addFlashAttribute("bookReadOnlyDTO", dto);
            return "redirect:/books/delete-success";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/success")
    public String bookSuccess(Model model) {
        return "book-success";
    }

    @GetMapping("/update-success")
    public String updateSuccess() {
        return "update-book-success";
    }

    @GetMapping("/delete-success")
    public String deleteSuccess() {
        return "delete-book-success";
    }

    @ModelAttribute("categoriesReadOnlyDTO")
    public List<CategoryReadOnlyDTO> categories() {
        return categoryService.findAllCategoriesSortedByName();
    }
}
