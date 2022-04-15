package com.ejder.socialapp.business.concretes;

import com.ejder.socialapp.business.abstracts.UserService;
import com.ejder.socialapp.business.request.auth.UserRegisterRequest;
import com.ejder.socialapp.core.business.BusinesRules;
import com.ejder.socialapp.core.utils.results.ErrorResult;
import com.ejder.socialapp.core.utils.results.Result;
import com.ejder.socialapp.core.utils.results.SuccessResult;
import com.ejder.socialapp.entity.User;
import com.ejder.socialapp.repository.UserRepository;
import com.ejder.socialapp.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=this.userRepository.getUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User did not found");
        }
        return JwtUserDetails.create(user);
    }

    @Override
    public Result addUser(UserRegisterRequest userRegisterRequest) {
        var response= BusinesRules.run(checkIfUserNameExist(userRegisterRequest.getUserName()));
        if(response==null){
            User addedUser=new User();
            addedUser.setUsername(userRegisterRequest.getUserName());
            addedUser.setEmail(userRegisterRequest.getEmail());
            addedUser.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            this.userRepository.save(addedUser);
            return new SuccessResult("Successfully registered");
        }return new ErrorResult(response.getMessage());

    }

    private Result checkIfUserNameExist(String userName){
        var user = this.userRepository.getUserByUsername(userName);
        if (user != null) {
            return new ErrorResult("This username have already exist");
        }return new SuccessResult();
    }

    private Result checkIfEmailExist(String email){
        var user=this.userRepository.getUserByEmail(email);
        if(user!=null){
            return new ErrorResult("This email have already exist");
        }return new SuccessResult();
    }
}
