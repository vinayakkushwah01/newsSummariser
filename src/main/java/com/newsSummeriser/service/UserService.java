package com.newsSummeriser.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.newsSummeriser.dto.UserDto;
import com.newsSummeriser.model.User;
import com.newsSummeriser.repository.UserRepository;





@Service
public class UserService {

    @Autowired
    private UserRepository userRepository ;
    // @Autowired
    // BookingService bookingService;


    public String getCurrentUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    public UserDto getUserDto(String email){
        User user = userRepository.findByEmail(email);
        UserDto newUserDto = new UserDto();
        newUserDto.setUserEmail(user.getEmail());
        newUserDto.setUserName(user.getName());
        newUserDto.setUserId(user.getId());
        return newUserDto;
    }

    public UserDto getCurrentUserDto(){
        return getUserDto(getCurrentUserUsername());
    }

    public User getUserFromUsername(String email){
       return  userRepository.findByEmail(email);
    }

    public User getCurrentLogedInUser(){
       return getUserFromUsername( getCurrentUserUsername());
    }


    
}
