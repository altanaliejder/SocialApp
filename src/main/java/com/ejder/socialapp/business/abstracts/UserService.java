package com.ejder.socialapp.business.abstracts;
import com.ejder.socialapp.business.request.auth.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.ejder.socialapp.core.utils.results.Result;

public interface UserService extends UserDetailsService {
    Result addUser(UserRegisterRequest userRegisterRequest);
}
