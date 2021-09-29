package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Attachment;
import uz.pdp.domain.Category;
import uz.pdp.domain.Client;
import uz.pdp.domain.Product;
import uz.pdp.model.ProductAddDto;
import uz.pdp.model.ProductDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.AttachmentRepo;
import uz.pdp.repository.CategoryRepo;
import uz.pdp.repository.ProductRepo;
import uz.pdp.service.ProductService;

import java.util.List;
import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final AttachmentRepo attachmentRepo;
    private final MapstructMapper mapstructMapper;


    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, AttachmentRepo attachmentRepo, MapstructMapper mapstructMapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.attachmentRepo = attachmentRepo;
        this.mapstructMapper = mapstructMapper;
    }


    @Override
    public ResponseEntity<ApiResponse<Product>> save(ProductAddDto dto) {
        Optional<Category> optionalCategory = categoryRepo.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category  id, %s not found!", dto.getCategoryId()), HttpStatus.NOT_FOUND);
        }
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(dto.getAttachmentId());
        if (!optionalAttachment.isPresent()) {
            return response(String.format("This File  id, %s not found!", dto.getAttachmentId()), HttpStatus.NOT_FOUND);
        }

        Product product = mapstructMapper.toProduct(dto);
        product.setCategory(optionalCategory.get());
        product.setAttachment(optionalAttachment.get());
        return response(productRepo.save(product));

    }

    @Override
    public ResponseEntity<ApiResponse<Product>> get(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        return optionalProduct.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Product id, %s not found!", id), HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<ApiResponse<List<Product>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Product> products = productRepo.findAll(pageable);
        if (products.isEmpty()) {
            return response("Products not found", HttpStatus.NOT_FOUND);
        }
        Long totalCount = productRepo.count();
        List<Product> list = products.getContent();
        return response(list,totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> update(Long id, ProductDto dto) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()) {
            return response(String.format("This Product  id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Optional<Category> optionalCategory = categoryRepo.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category  id, %s not found!", dto.getCategoryId()), HttpStatus.NOT_FOUND);
        }
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(dto.getAttachmentId());
        if (!optionalAttachment.isPresent()) {
            return response(String.format("This File  id, %s not found!", dto.getAttachmentId()), HttpStatus.NOT_FOUND);
        }

        Product product = mapstructMapper.toProduct(optionalProduct.get(), dto);
        product.setCategory(optionalCategory.get());
        product.setAttachment(optionalAttachment.get());
        return response(productRepo.save(product));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()) {
            return response(String.format("This Product id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        productRepo.delete(optionalProduct.get());
        return response(true);

    }
}
