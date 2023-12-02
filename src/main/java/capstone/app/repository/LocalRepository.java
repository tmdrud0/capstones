package capstone.app.repository;

import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Repository
public class LocalRepository {
    private final UserService userService;
    public LocalRepository(UserService userService) {
        Path imagePath = Paths.get("resources/images");
        Path pdfPath = Paths.get("resources/pdfs");
        try {
            Files.createDirectories(imagePath);
            Files.createDirectories(pdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.userService = userService;
    }

    public void saveImage(String path, MultipartFile file){
        try{
            Files.createDirectories(Path.of(path));
            Files.copy(file.getInputStream(), Path.of(path +"/" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new CustomException(ErrorCode.FILE_WRITE_FAIL);
        }
    }

    public void savePdf(Path path, MultipartFile file){
        try{
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new CustomException(ErrorCode.FILE_WRITE_FAIL);
        }
    }

    public void changeImageDir(String storedImagePath, String idPath) {
        File file = new File(storedImagePath);
        File newFile = new File(idPath);
        if(!file.renameTo(newFile)) throw new CustomException(ErrorCode.IMAGE_NAME_CHANGE_FAIL);

    }
}
