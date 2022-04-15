package com.ejder.socialapp.business.abstracts;

import com.ejder.socialapp.business.request.auth.UserLoginRequest;
import com.ejder.socialapp.business.request.auth.UserRegisterRequest;
import com.ejder.socialapp.business.response.auth.AuthResponse;
import com.ejder.socialapp.core.utils.results.DataResult;


public interface AuthService {
    DataResult<AuthResponse> login(UserLoginRequest userLoginRequest);
    DataResult<AuthResponse> register(UserRegisterRequest userRegisterRequest);
}
