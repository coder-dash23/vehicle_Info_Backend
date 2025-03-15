//package com.example.vehicle.service;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if ("user".equals(username)) {
//            return User.builder()
//                    .username("user")
//                    .password("{noop}password")  // Use {noop} for plain-text password
//                    .roles("USER")  // The only role available
//                    .build();
//        } else {
//            throw new UsernameNotFoundException("User not found");
//        }
//    }
//}
