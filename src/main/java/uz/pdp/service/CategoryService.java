package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Category;
import uz.pdp.model.CategoryAddDto;
import uz.pdp.model.CategoryDto;
import uz.pdp.model.response.ApiResponse;

import java.util.List;

public interface CategoryService {
    ResponseEntity<ApiResponse<Category>> save(CategoryAddDto dto);

    ResponseEntity<ApiResponse<Category>> get(Long id);

    ResponseEntity<ApiResponse<List<Category>>> getList(int page);

    ResponseEntity<ApiResponse<Category>> update(Long id, CategoryDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);
}
