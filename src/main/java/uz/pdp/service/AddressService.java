package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Address;
import uz.pdp.model.AddressDto;
import uz.pdp.model.response.ApiResponse;

import java.util.List;

public interface AddressService {
    ResponseEntity<ApiResponse<Address>> save(AddressDto dto);

    ResponseEntity<ApiResponse<Address>> get(Long id);

    ResponseEntity<ApiResponse<List<Address>>> getList(int page);

    ResponseEntity<ApiResponse<Address>> update(Long id, AddressDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);

}
