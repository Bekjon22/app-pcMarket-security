package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.domain.Client;
import uz.pdp.model.ClientDto;
import uz.pdp.model.response.ApiResponse;

import java.util.List;

public interface ClientService {
    ResponseEntity<ApiResponse<Client>> save(ClientDto dto);

    ResponseEntity<ApiResponse<Client>> get(Long id);

    ResponseEntity<ApiResponse<List<Client>>> getList(int page);

    ResponseEntity<ApiResponse<Client>> update(Long id, ClientDto dto);

    ResponseEntity<ApiResponse<Boolean>> delete(Long id);
}
