package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Category;
import uz.pdp.model.CategoryAddDto;
import uz.pdp.model.CategoryDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.CategoryRepo;
import uz.pdp.service.CategoryService;

import java.util.List;
import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, MapstructMapper mapstructMapper) {
        this.categoryRepo = categoryRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> save(CategoryAddDto dto) {
        boolean existsByName = categoryRepo.existsByName(dto.getName());
        if (existsByName) {
            return response(String.format("This name  [ %s ] already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        return response(categoryRepo.save(mapstructMapper.toCategory(dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> get(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        return optionalCategory.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Category id, %s not found!", id), HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<ApiResponse<List<Category>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Category> categories = categoryRepo.findAll(pageable);
        if (categories.isEmpty()) {
            return response("Categories not found", HttpStatus.NOT_FOUND);
        }
        Long totalCount = categoryRepo.count();
        List<Category> list = categories.getContent();
        return response(list,totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> update(Long id, CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category  id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        boolean exists = categoryRepo.existsByNameAndIdNot(dto.getName(), id);
        if (exists) {
            return response(String.format("This name  [ %s ] already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        return response(categoryRepo.save(mapstructMapper.toCategory(optionalCategory.get(),dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (!optionalCategory.isPresent()) {
            return response(String.format("This Category id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        categoryRepo.delete(optionalCategory.get());
        return response(true);
    }
}
