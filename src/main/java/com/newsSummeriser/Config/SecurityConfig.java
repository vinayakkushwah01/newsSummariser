package com.newsSummeriser.Config;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // this Will firt desable All the Default filters 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFillter jwtFillter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(customizer -> customizer.disable());// desable CSRF 
        
        http.authorizeHttpRequests(request -> request
        .requestMatchers("/login", "/register", "/api/breakingnews/**", "/api/news/**" ,
                         "/**", "/article/**", "/article","/category", "/category/**") // Public Endpoints
        .permitAll()
        .requestMatchers("/admin/**").hasRole("Admin") // Admin Restricted
        .requestMatchers("/user/**").hasAnyRole("Admin", "User") // User Restricted
        .anyRequest() // All Other Requests Require Authentication
        .authenticated());
        // but Still we not able to Acces the page 
        // for it we nned to Add form Login 
       //   http.formLogin(Customizer.withDefaults()); // Old form Login will re activted 
        http.httpBasic(Customizer.withDefaults()); // for the postman Rest acess
        http.sessionManagement(session ->
                     session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.addFilterBefore(jwtFillter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


 // this Works but we want from db and this is Hars coaded    
    // @Bean
    // public UserDetailsService userDetailsService(){

    //     UserDetails usee1= User
    //                         .withDefaultPasswordEncoder()
    //                         .username("kushwah")
    //                         .password("ram@123")
    //                         .roles("User")
    //                         .build();

    //     UserDetails usee2= User
    //                         .withDefaultPasswordEncoder()
    //                         .username("kushwah1")
    //                         .password("ram@123")
    //                         .roles("User")
    //                         .build();            
                            
    //     // We can Create Many users and There password form here Andf pass it to Bellow As Args
    //     return new InMemoryUserDetailsManager(usee1, usee2);
    // }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();


    }

     @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500","http://localhost:*")); // Allow Frontend Origin
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
