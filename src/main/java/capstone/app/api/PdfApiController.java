package capstone.app.api;

import capstone.app.api.dto.PdfDto;
import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.service.LocalService;
import capstone.app.service.OcrService;
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
    private final LocalService localService;
    private final PdfService pdfService;
    private final OcrService ocrService;

    //@PostMapping("/api/image")
    @PostMapping("/upload")
    public String uploadImage(@RequestBody MultipartFile file) {

        localService.saveImage(file);
        return "uploaded";
    }
    @PostMapping("/upload/totalWeight")
    public ResponseEntity uploadTotalWeight(@RequestBody MultipartFile file) {

        return ocrService.sendWeight(file);
    }
    @PostMapping("/upload/emptyWeight")
    public ResponseEntity uploadEmptyWeight(@RequestBody MultipartFile file) {

        return ocrService.sendWeight(file);
    }
    @PostMapping("/upload/carnum")
    public ResponseEntity uploadCarNum(@RequestBody MultipartFile file) {

        localService.saveImage(file);
        return ocrService.sendCar(file);
    }

    //@GetMapping("/api/recentPdf")
    @GetMapping("/recentPdf")
    public ResponseEntity<?> getRecentPdf() {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(pdfService.getRecentPdf());
    }

    //@GetMapping("/api/pdf/{id}")
    @GetMapping("/pdfs")
    public ResponseEntity<?> getById(@RequestBody Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(pdfService.getById(id));
    }

    //@GetMapping("/api/pdfs")
    @GetMapping("/transactionList")
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
