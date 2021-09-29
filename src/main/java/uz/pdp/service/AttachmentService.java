package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.model.response.ApiResponse;


public interface AttachmentService {
    ResponseEntity<ApiResponse<String>> uploadFile(MultipartHttpServletRequest request);
}
