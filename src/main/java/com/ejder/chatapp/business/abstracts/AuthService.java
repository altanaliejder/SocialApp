package com.ejder.chatapp.business.abstracts;

import com.ejder.chatapp.business.request.UserLoginRequest;
import com.ejder.chatapp.business.request.UserRegisterRequest;
import com.ejder.chatapp.business.response.AuthResponse;
import com.ejder.chatapp.core.utils.results.DataResult;


public interface AuthService {
    DataResult<AuthResponse> login(UserLoginRequest userLoginRequest);
    DataResult<AuthResponse> register(UserRegisterRequest userRegisterRequest);
}
