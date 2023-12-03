package capstone.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다."),

    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, ""),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.NOT_FOUND, "잘못된 URL입니다."),
    PDF_NOT_FOUND(HttpStatus.NOT_FOUND,"pdf파일이 없습니다."),
    PDF_READ_FAIL(HttpStatus.NOT_FOUND,"pdf파일을 읽는데 실패했습니다."),
    
    
    
    ALREADY_PRESENT(HttpStatus.BAD_REQUEST, "이미 존재합니다."),
    FAIL_LOGIN(HttpStatus.BAD_REQUEST, "잘못된 아이디 혹은 비밀번호 입니다."),
    
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),


    FILE_WRITE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"파일 쓰기에 실패했습니다."),
    IMAGE_NAME_CHANGE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"이미지 디렉토리 이름 변경에 실패했습니다."),
    OCR_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"OCR에 실패했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
