package com.example.firebasedummyserver.controller;

import com.example.firebasedummyserver.model.entities.EntityUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.firebasedummyserver.service.UserService;

@RestController
public class UserController {
   
   @Autowired
   private UserService userService;

   @PostMapping("/basic/user/google")
   public @ResponseBody ResponseEntity<EntityUser> createBasicUser(){
      try {
         EntityUser user = (EntityUser) SecurityContextHolder.getContext().getAuthentication();
         EntityUser savedUser = userService.addBasicUser(user);
         
         return new ResponseEntity<EntityUser>(savedUser, HttpStatus.CREATED);

      } catch (DataIntegrityViolationException e ){
         return new ResponseEntity<>(HttpStatus.CONFLICT);
      } catch (IllegalArgumentException e) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      
   }
   @PostMapping("/admin/user/google")
   public @ResponseBody ResponseEntity<EntityUser> createAdminUser(){
      try {
         EntityUser user = (EntityUser) SecurityContextHolder.getContext().getAuthentication();
         EntityUser savedUser = userService.addAdminUser(user);
         
         return new ResponseEntity<EntityUser>(savedUser, HttpStatus.CREATED);

      } catch (DataIntegrityViolationException e ){
         return new ResponseEntity<>(HttpStatus.CONFLICT);
      } catch (IllegalArgumentException e) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      
   }
}
