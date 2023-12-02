package capstone.app.api;

import capstone.app.api.dto.PdfDto;
import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.service.LocalService;
import capstone.app.service.PdfService;
import capstone.app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PdfApiController {
    private final UserService userService;
    private final LocalService localService;
    private final PdfService pdfService;

    //@PostMapping("/api/image")
    @PostMapping("/upload")
    public UploadResponse uploadImage(@RequestBody MultipartFile file) {

        localService.saveImage(file);

        return new UploadResponse("test-string");
    }
    @PostMapping("/upload/totalWeight")
    public UploadResponse uploadTotalWeight(@RequestBody MultipartFile file) {

        localService.saveImage(file);

        return new UploadResponse("test-string");
    }
    @PostMapping("/upload/emptyWeight")
    public UploadResponse uploadEmptyWeight(@RequestBody MultipartFile file) {

        localService.saveImage(file);

        return new UploadResponse("test-string");
    }
    @PostMapping("/upload/carnum")
    public UploadResponse uploadCarNum(@RequestBody MultipartFile file) {

        localService.saveImage(file);

        return new UploadResponse("test-string");
    }

    //@GetMapping("/api/recentPdf")
    @GetMapping("/recentPdf")
    public ResponseEntity<?> getRecentPdf() {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(pdfService.getRecentPdf());
    }

    //@GetMapping("/api/pdf/{id}")
    @GetMapping("/pdfs/{id}")
    public ResponseEntity<?> getById(@RequestHeader Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(pdfService.getById(id));
    }

    @GetMapping("/pdfs")
    public List<PdfDto> getAllOfMine() {
        return pdfService.getAllOfMine();
    }

    @Getter
    @AllArgsConstructor
    static class UploadResponse{
        private String result;
    }

    @PostMapping("/test/pdf")
    public ResponseEntity test() {
        try {
            FileSystemResource resource = new FileSystemResource("t.pdf");
            if (!resource.exists()) {
                throw new CustomException(ErrorCode.PDF_NOT_FOUND);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(resource);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.PDF_READ_FAIL);
        }


    }
}
