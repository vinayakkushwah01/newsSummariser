package com.newsSummeriser.Config;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsSummeriser.dto.LoginRequest;
import com.newsSummeriser.dto.RegisterRequest;
import com.newsSummeriser.model.Role;
import com.newsSummeriser.model.User;
import com.newsSummeriser.repository.RoleRepository;
import com.newsSummeriser.repository.UserRepository;

@Service
public class OpenAuthService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // public User registerUser(RegisterRequest rU1){
    //     User u1 = new User();
    //     u1.setEmail(rU1.getEmail());
    //     u1.setName(rU1.getName());
    //     u1.setPassword(encoder.encode(rU1.getPassword()));
    //     u1.setRole(rU1.getRole());
    //     return userRepository.save(u1);
    // }

    public User registerUser(RegisterRequest rU1) {
        // Validate email uniqueness (assuming email is unique)
        if (userRepository.findByEmail(rU1.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Validate role existence (assuming role validation is needed)
        Role role = roleRepository.findById(rU1.getRole().getId()).orElse(null);
        if (role == null) {
            throw new RuntimeException("Invalid role provided");
        }

        User u1 = new User();
        u1.setEmail(rU1.getEmail());
        u1.setName(rU1.getName());
        u1.setPassword(encoder.encode(rU1.getPassword()));
        u1.setRole(role);

        return userRepository.save(u1);
    }

    public String verifyLogin(LoginRequest lReq){
            
        Authentication authentication = 
            authManager.authenticate(new UsernamePasswordAuthenticationToken(lReq.getEmail(), lReq.getPassword()));
        
        if(authentication.isAuthenticated()){

            System.out.println("### L Req Email "+ lReq.getEmail());
            String role = userRepository.findByEmail(lReq.getEmail()).getRole().getName();
            System.out.println("#####  Role +="+role);
            return jwtService.generateToken(lReq.getEmail(), role);
        }
        return "fail";
        
    }

}

