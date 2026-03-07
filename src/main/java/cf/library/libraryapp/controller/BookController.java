package cf.library.libraryapp.controller;

import cf.library.libraryapp.dto.BookInsertDTO;
import cf.library.libraryapp.dto.BookReadOnlyDTO;
import cf.library.libraryapp.dto.CategoryReadOnlyDTO;
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

    @GetMapping("/insert")
    public String getBookForm(Model model) {
        model.addAttribute("BookInsertDTO", BookInsertDTO.empty());
        return "book-insert";
    }

    @PostMapping("/insert")
    public String bookInsert(@Valid @ModelAttribute("BookInsertDTO") BookInsertDTO bookInsertDTO,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book-insert";
        }

        BookReadOnlyDTO bookReadOnlyDTO = new BookReadOnlyDTO("", "", "");

        try {
            redirectAttributes.addFlashAttribute("BookReadOnlyDTO", bookReadOnlyDTO);
        } catch ()
    }

    @GetMapping("/success")
    public String bookSuccess(Model model) {
        return "book-success";
    }

    @ModelAttribute("CategoryReadOnlyDTO")
    public List<CategoryReadOnlyDTO> categories() {
        return List.of(
                new CategoryReadOnlyDTO(1L, "Sci-Fi"),
                new CategoryReadOnlyDTO(2L, "Comedy"),
                new CategoryReadOnlyDTO(3L, "Fantasy"));
    }
}
