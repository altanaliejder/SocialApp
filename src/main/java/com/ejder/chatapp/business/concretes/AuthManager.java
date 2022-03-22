package com.ejder.chatapp.business.concretes;

import com.ejder.chatapp.business.abstracts.AuthService;
import com.ejder.chatapp.business.abstracts.UserService;
import com.ejder.chatapp.business.request.UserLoginRequest;
import com.ejder.chatapp.business.request.UserRegisterRequest;
import com.ejder.chatapp.business.response.AuthResponse;
import com.ejder.chatapp.core.utils.results.DataResult;
import com.ejder.chatapp.core.utils.results.ErrorDataResult;
import com.ejder.chatapp.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ejder.chatapp.core.utils.results.SuccessDataResult;

@Service
public class AuthManager implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthManager(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public DataResult<AuthResponse> login(UserLoginRequest userLoginRequest) {
        return getAuthResponse(userLoginRequest.getUserName(), userLoginRequest.getPassword());
    }

    @Override
    public DataResult<AuthResponse> register(UserRegisterRequest userRegisterRequest) {
        var response=this.userService.addUser(userRegisterRequest);
        if(response.isSuccess())
            return getAuthResponse(userRegisterRequest.getUserName(),userRegisterRequest.getPassword());
        return new ErrorDataResult<AuthResponse>(response.getMessage());
    }

    private DataResult<AuthResponse> getAuthResponse(String userName, String password) {
        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userName, password);
        Authentication auth =authenticationManager.authenticate(authToken);
        System.out.println(auth);
        //SecurityContextHolder.getContext().setAuthentication(auth);
        String token=jwtTokenProvider.generateJwtToken(auth);
        AuthResponse response=new AuthResponse();
        response.setToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new SuccessDataResult<AuthResponse>(response);
    }
}
