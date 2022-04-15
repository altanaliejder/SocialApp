package com.ejder.socialapp.controller;
import com.ejder.socialapp.business.abstracts.AuthService;
import com.ejder.socialapp.business.request.auth.UserLoginRequest;
import com.ejder.socialapp.business.request.auth.UserRegisterRequest;
import com.ejder.socialapp.business.response.auth.AuthResponse;
import com.ejder.socialapp.core.utils.results.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public DataResult<AuthResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        return this.authService.login(userLoginRequest);
    }

    @PostMapping("register")
    public DataResult<AuthResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return this.authService.register(userRegisterRequest);
    }
}
