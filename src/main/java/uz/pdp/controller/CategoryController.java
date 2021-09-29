package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Category;
import uz.pdp.model.CategoryAddDto;
import uz.pdp.model.CategoryDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.CategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize(value = "hasAuthority('ADD_CATEGORY')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Category>> save(@Valid @RequestBody CategoryAddDto dto){
        return categoryService.save(dto);
    }

    @PreAuthorize(value = "hasAuthority('GET_ONE_CATEGORY')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Category>> get(@PathVariable(value = "id") Long id) {
        return categoryService.get(id);
    }

    @PreAuthorize(value = "hasAuthority('GET_ALL_CATEGORY')")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Category>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return categoryService.getList(page);
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_CATEGORY')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody CategoryDto dto) {
        return categoryService.update(id, dto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CATEGORY')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return categoryService.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
