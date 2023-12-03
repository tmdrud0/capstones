package capstone.app.service;

import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
public class OcrService {
    private final String CAR_URL = "http://220.149.231.137:7016/car";
    private final String WEIGHT_URL = "http://220.149.231.137:7016/digit";

    public ResponseEntity<String> send(String url, MultipartFile file){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body
                    = new LinkedMultiValueMap<>();
             body.add("file", file.getResource());

            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate
                    .postForEntity(url, requestEntity, String.class);

            return response;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.OCR_FAIL);
        }
    }

    public ResponseEntity<String> sendCar(MultipartFile file){
        return send(CAR_URL, file);
    }
    public ResponseEntity<String> sendWeight(MultipartFile file){
        return send(WEIGHT_URL, file);
    }
}
