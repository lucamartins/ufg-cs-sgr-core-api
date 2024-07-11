package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.service.auth.AuthService;
import sgr.com.sgrcoreapi.service.auth.dto.UserLoginRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        String jwtToken = authService.login(userLoginRequest);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", 200);
        responseMap.put("jwt", jwtToken);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        var message = authService.logout();
        var response = new ApiResponse<String>(
                HttpStatus.OK.value(),
                message,
                null
        );
        return ResponseEntity.ok(response);
    }
}
