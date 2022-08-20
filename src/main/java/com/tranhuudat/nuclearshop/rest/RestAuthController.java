package com.tranhuudat.nuclearshop.rest;

import com.tranhuudat.nuclearshop.request.LoginRequest;
import com.tranhuudat.nuclearshop.request.RegisterRequest;
import com.tranhuudat.nuclearshop.response.AuthResponse;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class RestAuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<?>> signup(@RequestBody @Valid RegisterRequest registerRequest) {
        log.info("api signup request at {}", LocalDateTime.now());
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<BaseResponse<?>> verifyAccount(@PathVariable String token) {
        return ResponseEntity.ok( authService.verifyAccount(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok( authService.login(loginRequest));
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<String> refreshTokens() {

        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return null;
    }
}
