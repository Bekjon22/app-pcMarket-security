package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Product;
import uz.pdp.model.ProductAddDto;
import uz.pdp.model.ProductDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.ProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Product>> save(@Valid @RequestBody ProductAddDto dto) {
        return productService.save(dto);
    }

    @PreAuthorize(value = "hasAuthority('GET_ONE_PRODUCT')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Product>> get(@PathVariable(value = "id") Long id) {
        return productService.get(id);
    }

    @PreAuthorize(value = "hasAuthority('GET_ALL_PRODUCT')")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Product>>> getList(@RequestParam(required = false, defaultValue = "0") int page) {
        return productService.getList(page);
    }


    @PreAuthorize(value = "hasAuthority('UPDATE_PRODUCT')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody ProductDto dto) {
        return productService.update(id, dto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return productService.delete(id);
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
