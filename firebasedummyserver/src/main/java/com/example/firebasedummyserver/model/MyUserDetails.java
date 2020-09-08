package com.example.firebasedummyserver.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.firebasedummyserver.model.entities.EntityRole;
import com.example.firebasedummyserver.model.entities.EntityUser;
import com.google.cloud.storage.Acl.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private EntityUser user;

   public MyUserDetails(EntityUser user){
      this.user = user;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      List<EntityRole> roles = user.getRoles();
      List<SimpleGrantedAuthority> authorities= new ArrayList<>();
      
      for (EntityRole role : roles){
         authorities.add(new SimpleGrantedAuthority(role.getName()));
      }
      
      return authorities;
   }

   @Override
   public String getPassword() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getUsername() {
      return user.getUsername();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isEnabled() {
      // TODO Auto-generated method stub
      return true;
   }

   /**
    * @return the user
    */
   public EntityUser getUser() {
      return user;
   }
   
}
