package com.example.firebasedummyserver.utils;

public class UserUtil {
   public static String extractUsernameFromEmail(String email){
      return email.split("@")[0];
   }
}
