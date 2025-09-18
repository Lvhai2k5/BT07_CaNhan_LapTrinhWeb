package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.service.CategoryService;
import vn.iotstar.entity.CategoryEntity;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/categories"})
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "list";
    }

    @GetMapping("/category/add")
    public String addForm(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "AddOrEdit";
    }

    @GetMapping("/category/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Optional<CategoryEntity> opt = categoryService.findById(id);
        if (opt.isPresent()) {
            model.addAttribute("category", opt.get());
            return "AddOrEdit";
        }
        return "redirect:/categories";
    }

    @PostMapping("/category/save")
    public String save(@ModelAttribute("category") CategoryEntity category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }
}
