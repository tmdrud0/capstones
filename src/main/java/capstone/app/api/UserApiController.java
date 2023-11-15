package capstone.app.api;

import capstone.app.api.dto.UserDto;
import capstone.app.domain.User;
import capstone.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
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

}