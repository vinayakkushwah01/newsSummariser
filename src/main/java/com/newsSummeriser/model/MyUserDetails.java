package com.newsSummeriser.model;



import java.util.Collection;
import java.util.Collections;
import java.util.Set;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MyUserDetails  implements UserDetails{

    private User user;
    public MyUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() != null) {
            return Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        } else {
            return Collections.emptyList(); 
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

}
