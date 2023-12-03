package capstone.app.api;

import capstone.app.api.dto.UserDto;
import capstone.app.domain.Company;
import capstone.app.domain.User;
import capstone.app.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody SignUpRequest request
    ) {
        return ResponseEntity.ok(userService.signup(request.toUser()));
    }

    @PutMapping("/api/user")
    public ResponseEntity<User> changeInfo(
            @RequestBody SignUpRequest request
    ) {
        return ResponseEntity.ok(userService.putUser(request.toUser()));
    }

    @GetMapping("/api/user")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/api/user/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }

    @GetMapping("/api/user/deal_product")
    public Result getDealProductsWithMe() {
        return new Result(userService.getDealProductsWithMe());
    }
    @GetMapping("/api/user/deal_company")
    public Result getDealCompaniesWithMe() {
        return new Result(userService.getDealCompaniesWithMe());
    }

    @GetMapping("/api/test")
    public String test() {
        return "test ok";
    }

    @Data
    @AllArgsConstructor
    static class SignUpRequest{
        @NotNull(message = "id가 비어있을 수 없습니다!")
        @Size(min = 3, max = 50, message = "id 길이 이상")
        String username;
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull(message = "password가 비어있을 수 없습니다!")
        @Size(min = 3, max = 100)
        String password;
        @NotNull
        @Size(min = 3, max = 50)
        String name;
        @NotNull
        @Size(min = 3, max = 50)
        String email;
        String CP;
        String BN;
        String Addr;
        String TP;
        String FAX;

        public User toUser(){
            Company company = new Company(BN,Addr,TP,FAX,email);
            User user = new User(username, name, CP, company);
            user.setPassword(password);
            return user;
        }
    }
}