package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Product;
import uz.pdp.model.ProductAddDto;
import uz.pdp.model.ProductDto;
import uz.pdp.model.response.ApiResponse;

import java.util.List;

public interface ProductService {
    ResponseEntity<ApiResponse<Product>> save(ProductAddDto dto);

    ResponseEntity<ApiResponse<Product>> get(Long id);

    ResponseEntity<ApiResponse<List<Product>>> getList(int page);

    ResponseEntity<ApiResponse<Product>> update(Long id, ProductDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

}
