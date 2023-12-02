package capstone.app.service;


import capstone.app.domain.Measurement;
import capstone.app.repository.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LocalService {
    private final LocalRepository localRepository;
    private final UserService userService;
    public void saveImage(MultipartFile file) {
        String storedImagePath = "resources/images/"+userService.getMyUserWithAuthorities().get().getUsername() + "/new";
        localRepository.saveImage(storedImagePath, file);
    }

    public void changeNewToId(Long id){
        String path = "resources/images/"+userService.getMyUserWithAuthorities().get().getUsername() + "/";
        String storedImagePath = path + "new";
        String idPath = path + id;
        localRepository.changeImageDir(storedImagePath, idPath);
    }
}
