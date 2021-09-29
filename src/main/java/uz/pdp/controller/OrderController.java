package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Order;
import uz.pdp.model.OrderDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.OrderService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PreAuthorize(value = "hasAuthority('ADD_ORDER')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Order>> save(@Valid @RequestBody OrderDto dto){
        return orderService.save(dto);
    }

    @PreAuthorize(value = "hasAuthority('GET_ONE_ORDER')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Order>> get(@PathVariable(value = "id") Long id) {
        return orderService.get(id);
    }

    @PreAuthorize(value = "hasAuthority('GET_ALL_ORDER')")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Order>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return orderService.getList(page);
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_ORDER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Order>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody OrderDto dto) {
        return orderService.update(id, dto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ORDER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return orderService.delete(id);
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
