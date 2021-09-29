package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.domain.Client;
import uz.pdp.model.ClientDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.service.ClientService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize(value = "hasAuthority('ADD_CLIENT')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Client>> save(@Valid @RequestBody ClientDto dto){
        return clientService.save(dto);
    }

    @PreAuthorize(value = "hasAuthority('GET_ONE_CLIENT')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Client>> get(@PathVariable(value = "id") Long id) {
        return clientService.get(id);
    }

    @PreAuthorize(value = "hasAuthority('GET_ALL_CLIENT')")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Client>>> getList(@RequestParam(required = false,defaultValue = "0") int page) {
        return clientService.getList(page);
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_CLIENT')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Client>> update(@Valid @PathVariable(value = "id") Long id, @RequestBody ClientDto dto) {
        return clientService.update(id, dto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_CLIENT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return clientService.delete(id);
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
