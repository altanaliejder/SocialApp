package com.ejder.chatapp.business.abstracts;
import com.ejder.chatapp.business.request.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.ejder.chatapp.core.utils.results.Result;

public interface UserService extends UserDetailsService {
    Result addUser(UserRegisterRequest userRegisterRequest);
}
