package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Order;
import uz.pdp.model.OrderDto;
import uz.pdp.model.response.ApiResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<ApiResponse<Order>> save(OrderDto dto);

    ResponseEntity<ApiResponse<Order>> get(Long id);

    ResponseEntity<ApiResponse<List<Order>>> getList(int page);

    ResponseEntity<ApiResponse<Order>> update(Long id, OrderDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);
}
