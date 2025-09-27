package vn.iotstar.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor injection (không dùng Lombok)
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 📌 Danh sách + tìm kiếm + phân trang
    @GetMapping
    public String list(@RequestParam(value = "q", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model) {

        Page<Category> result = categoryService.search(keyword, page, size);
        model.addAttribute("page", result);
        model.addAttribute("q", keyword);
        return "categories/list";
    }

    // 📌 Form thêm mới
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("title", "Thêm danh mục");
        return "categories/form";
    }

    // 📌 Xử lý thêm mới
    @PostMapping
    public String create(@Valid @ModelAttribute("category") Category category,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Thêm danh mục");
            return "categories/form";
        }
        categoryService.save(category);
        return "redirect:/categories?created";
    }

    // 📌 Form sửa
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("title", "Cập nhật danh mục");
        return "categories/form";
    }

    // 📌 Xử lý sửa
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("category") Category category,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Cập nhật danh mục");
            return "categories/form";
        }
        category.setId(id);
        categoryService.save(category);
        return "redirect:/categories?updated";
    }

    // 📌 Xử lý xoá
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories?deleted";
    }
}
