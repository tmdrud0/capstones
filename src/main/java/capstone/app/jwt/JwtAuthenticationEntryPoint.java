package capstone.app.jwt;

import capstone.app.exception.BadCredentialHandler;
import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.exception.ErrorResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import org.json.simple.JSONObject;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @AllArgsConstructor
    class Excep{
        public Long status;
        public String code;
        public String message;
    }
    private Excep failLogin =
            new Excep(Long.valueOf(HttpStatus.UNAUTHORIZED.value()), "FAIL_LOGIN","잘못된 아이디 혹은 비밀번호입니다.");
    private Excep invalidToken =
            new Excep(Long.valueOf(HttpStatus.UNAUTHORIZED.value()), "INVALID_AUTH_TOKEN","권한이 없습니다.");
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {


        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");


        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            if(authException instanceof BadCredentialsException){
                objectMapper.writeValue(os, failLogin);
            }
            else{
                objectMapper.writeValue(os, invalidToken);
            }
            os.flush();
        }

    }
}