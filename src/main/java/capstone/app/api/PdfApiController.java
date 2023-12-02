package capstone.app.api;

import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class PdfApiController {
    private final UserService userService;

    @PostMapping("/upload")
    public UploadResponse test(@RequestParam MultipartFile file) {

        String originName = file.getOriginalFilename();
        String storedImagePath = "./resources/"+userService.getMyUserWithAuthorities().get().getUsername()+"/"+originName;
        try {
            file.transferTo(new File(storedImagePath));
        } catch (IOException e){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return new UploadResponse("test-string");
    }


    @GetMapping("/pdf")
    public ResponseEntity<?> downloadImage() {
        try {
            String path = "./t.pdf";
            FileSystemResource resource = new FileSystemResource(path);
            if (!resource.exists()) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    @Getter
    @AllArgsConstructor
    static class UploadResponse{
        private String result;
    }

}
