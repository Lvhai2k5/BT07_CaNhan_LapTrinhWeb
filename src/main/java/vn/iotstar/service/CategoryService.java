package vn.iotstar.service;

import org.springframework.data.domain.Page;
import vn.iotstar.entity.Category;

public interface CategoryService {

    Page<Category> search(String keyword, int page, int size);

    Category findById(Long id);

    Category save(Category category);

    void deleteById(Long id);
}
