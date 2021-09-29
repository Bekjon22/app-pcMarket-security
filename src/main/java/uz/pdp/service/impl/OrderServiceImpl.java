package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.domain.Client;
import uz.pdp.domain.Order;
import uz.pdp.domain.Product;
import uz.pdp.model.OrderDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.ClientRepo;
import uz.pdp.repository.OrderRepo;
import uz.pdp.repository.ProductRepo;
import uz.pdp.service.OrderService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ClientRepo clientRepo;
    private final ProductRepo productRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, ClientRepo clientRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.productRepo = productRepo;
    }


    @Override
    public ResponseEntity<ApiResponse<Order>> save(OrderDto dto) {
        Optional<Client> optionalClient = clientRepo.findById(dto.getClientId());
        if (!optionalClient.isPresent()) {
            return response(String.format("This Client  id, %s not found!", dto.getClientId()), HttpStatus.NOT_FOUND);
        }

        Set<Product> products = new HashSet<>();
        for (Long aLong : dto.getProductsId()) {

            if (!productRepo.findById(aLong).isPresent()) {
                return response(String.format("This Product id, %s not found!", aLong), HttpStatus.NOT_FOUND);
            }
            if (!productRepo.findById(aLong).get().isActive()){
                return response(String.format("This Product is [ %s ] inactive!", aLong), HttpStatus.CONFLICT);
            }
            products.add(productRepo.findById(aLong).get());
        }

        Order order = new Order();
        order.setClient(optionalClient.get());
        order.setProducts(products);

        return response(orderRepo.save(order));


    }

    @Override
    public ResponseEntity<ApiResponse<Order>> get(Long id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        return optionalOrder.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Order id, %s not found!", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<List<Order>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Order> orders = orderRepo.findAll(pageable);
        if (orders.isEmpty()) {
            return response("Orders not found", HttpStatus.NOT_FOUND);
        }
        Long totalCount = orderRepo.count();
        List<Order> list = orders.getContent();
        return response(list,totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Order>> update(Long id, OrderDto dto) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (!orderOptional.isPresent()) {
            return response(String.format("This Order  id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Optional<Client> optionalClient = clientRepo.findById(dto.getClientId());
        if (!optionalClient.isPresent()) {
            return response(String.format("This Client  id, %s not found!", dto.getClientId()), HttpStatus.NOT_FOUND);
        }

        Set<Product> products = new HashSet<>();
        for (Long aLong : dto.getProductsId()) {

            if (!productRepo.findById(aLong).isPresent()) {
                return response(String.format("This Product id, %s not found!", aLong), HttpStatus.NOT_FOUND);
            }
            if (!productRepo.findById(aLong).get().isActive()){
                return response(String.format("This Product is [ %s ] inactive!", aLong), HttpStatus.CONFLICT);
            }
            products.add(productRepo.findById(aLong).get());
        }

        Order order = orderOptional.get();
        order.setClient(optionalClient.get());
        order.setProducts(products);
        return response(orderRepo.save(order));

    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        if (!optionalOrder.isPresent()) {
            return response(String.format("This Order id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        orderRepo.delete(optionalOrder.get());
        return response(true);
    }
}
