package com.example.firebasedummyserver.service;

import com.example.firebasedummyserver.model.MyUserDetails;
import com.example.firebasedummyserver.model.entities.EntityUser;
import com.example.firebasedummyserver.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   @Autowired
   private UserRepository userRepository;

   @Override
   public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      EntityUser user = userRepository.findByUsername(username);

      if (user == null) {
         return null;
      }

      return new MyUserDetails(user);
   }
}
