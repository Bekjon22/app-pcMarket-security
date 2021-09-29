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
import uz.pdp.model.AddressDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.AddressRepo;
import uz.pdp.service.AddressService;

import java.util.List;
import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo, MapstructMapper mapstructMapper) {
        this.addressRepo = addressRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Address>> save(AddressDto dto) {
        return response(addressRepo.save(mapstructMapper.toAddress(dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Address>> get(Long id) {
        Optional<Address> optionalAddress = addressRepo.findById(id);
        return optionalAddress.map(ApiResponse::response).orElseGet(() ->
                response(String.format("This Address id, %s not found!", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<List<Address>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Address> addresses = addressRepo.findAll(pageable);
        if (addresses.isEmpty()) {
            return response("Addresses not found", HttpStatus.NOT_FOUND);
        }
        Long totalCount = addressRepo.count();
        List<Address> list = addresses.getContent();
        return response(list,totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Address>> update(Long id, AddressDto dto) {
        Optional<Address> optionalAddress = addressRepo.findById(id);
        if (!optionalAddress.isPresent()) {
            return response(String.format("This Address  id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        return response(addressRepo.save(mapstructMapper.toAddress(optionalAddress.get(),dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<Address> optionalAddress = addressRepo.findById(id);
        if (!optionalAddress.isPresent()) {
            return response(String.format("This Address id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        addressRepo.delete(optionalAddress.get());
        return response(true);
    }
}
