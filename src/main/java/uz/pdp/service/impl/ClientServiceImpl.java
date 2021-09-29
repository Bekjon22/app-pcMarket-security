package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Address;
import uz.pdp.domain.Category;
import uz.pdp.domain.Client;
import uz.pdp.model.ClientDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.AddressRepo;
import uz.pdp.repository.ClientRepo;
import uz.pdp.service.ClientService;

import java.util.List;
import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final AddressRepo addressRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public ClientServiceImpl(ClientRepo clientRepo, AddressRepo addressRepo, MapstructMapper mapstructMapper) {
        this.clientRepo = clientRepo;
        this.addressRepo = addressRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> save(ClientDto dto) {
        boolean byPhoneNumber = clientRepo.existsByPhoneNumber(dto.getPhoneNumber());
        if (byPhoneNumber) {
            return response(String.format("This PhoneNumber  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.CONFLICT);
        }
        Optional<Address> optionalAddress = addressRepo.findById(dto.getAddressId());
        if (!optionalAddress.isPresent()) {
            return response(String.format("This Address  id, %s not found!", dto.getAddressId()), HttpStatus.NOT_FOUND);
        }
        Client client = mapstructMapper.toClient(dto);
        client.setAddress(optionalAddress.get());
        return response(clientRepo.save(client));
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> get(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        return optionalClient.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Client id, %s not found!", id), HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<ApiResponse<List<Client>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Client> clients = clientRepo.findAll(pageable);
        if (clients.isEmpty()) {
            return response("Clients not found", HttpStatus.NOT_FOUND);
        }
        Long totalCount = clientRepo.count();
        List<Client> list = clients.getContent();
        return response(list,totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> update(Long id, ClientDto dto) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            return response(String.format("This Client  id, %s not found!", id), HttpStatus.NOT_FOUND);
        }

        boolean andIdNot = clientRepo.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id);
        if (andIdNot) {
            return response(String.format("This PhoneNumber  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.CONFLICT);
        }
        Optional<Address> optionalAddress = addressRepo.findById(dto.getAddressId());
        if (!optionalAddress.isPresent()) {
            return response(String.format("This Address  id, %s not found!", dto.getAddressId()), HttpStatus.NOT_FOUND);
        }

        Client client = mapstructMapper.toClient(optionalClient.get(), dto);
        client.setAddress(optionalAddress.get());
        return response(clientRepo.save(client));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Client> optionalClient = clientRepo.findById(id);
        if (!optionalClient.isPresent()) {
            return response(String.format("This Client id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        clientRepo.delete(optionalClient.get());
        return response(true);
    }
}
