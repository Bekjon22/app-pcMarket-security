package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Address;
import uz.pdp.model.AddressDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }



    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Address>> save(@Valid @RequestBody AddressDto dto){
        return addressService.save(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Address>> get(@PathVariable(value = "id") Long id) {
        return addressService.get(id);
    }
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Address>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return addressService.getList(page);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Address>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody AddressDto dto) {
        return addressService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return addressService.delete(id);
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
