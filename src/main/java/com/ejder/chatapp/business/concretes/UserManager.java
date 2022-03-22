package com.ejder.chatapp.business.concretes;

import com.ejder.chatapp.business.abstracts.UserService;
import com.ejder.chatapp.business.request.UserRegisterRequest;
import com.ejder.chatapp.core.business.BusinesRules;
import com.ejder.chatapp.core.utils.results.ErrorResult;
import com.ejder.chatapp.core.utils.results.Result;
import com.ejder.chatapp.core.utils.results.SuccessResult;
import com.ejder.chatapp.entity.User;
import com.ejder.chatapp.repository.UserRepository;
import com.ejder.chatapp.security.JwtUserDetails;
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
        var response= BusinesRules.run(checkIfUserExist(userRegisterRequest.getUserName()));
        if(response==null){
            User addedUser=new User();
            addedUser.setUsername(userRegisterRequest.getUserName());
            addedUser.setEmail(userRegisterRequest.getEmail());
            addedUser.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            this.userRepository.save(addedUser);
            return new SuccessResult("Successfully registered");
        }return new ErrorResult(response.getMessage());

    }

    private Result checkIfUserExist(String userName){
        var user = this.userRepository.getUserByUsername(userName);
        if (user != null) {
            return new ErrorResult("This user have already exist");
        }return new SuccessResult();
    }
}
