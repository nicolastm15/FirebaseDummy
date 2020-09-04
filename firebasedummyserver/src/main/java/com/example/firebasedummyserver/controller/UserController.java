package com.example.firebasedummyserver.controller;

import com.example.firebasedummyserver.model.entities.EntityUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.firebasedummyserver.service.UserService;

@RestController
public class UserController {
   
   @Autowired
   private UserService userService;

   @GetMapping("/googleuser")
   public @ResponseBody ResponseEntity<EntityUser> createUser(){
      try {
         EntityUser user = (EntityUser) SecurityContextHolder.getContext().getAuthentication();
         EntityUser savedUser = userService.addBasicUser(user);
         if (savedUser != null)
            return new ResponseEntity<EntityUser>(savedUser, HttpStatus.CREATED);
         return new ResponseEntity<>(HttpStatus.CONFLICT);

      } catch (IllegalArgumentException e) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } catch (DataIntegrityViolationException e ){
         return new ResponseEntity<>(HttpStatus.CONFLICT);
      }
      
   }
}
