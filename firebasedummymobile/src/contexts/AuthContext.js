import React, {createContext, useState} from 'react';

import auth from '@react-native-firebase/auth';
import {
  GoogleSignin,
  GoogleSigninButton,
} from '@react-native-community/google-signin';
import googleServicesCredentials from '../../android/app/google-services.json';

//clientId do app relacionada com o google.
const {
  client_id: googleClientId,
} = googleServicesCredentials.client[0].oauth_client[1];

GoogleSignin.configure({
  webClientId: googleClientId,
});


export const AuthContext = createContext({
  isSignedIn: null,
  user: null,
  firebaseIdToken:null,
  handleSignIn:null,
  handleSignOut:null
});

export const AuthProvider = ({children}) => {
   const [user, setUser] = useState(null);
   const [firebaseIdToken, setFirebaseIdToken] = useState(null);

   async function handleGoogleSignIn() {
     try {
       const response = await GoogleSignin.signIn();
       const {idToken: googleIdToken} = response;
       const googleCredential = auth.GoogleAuthProvider.credential(
         googleIdToken,
       );
       await auth().signInWithCredential(googleCredential);
       const firebaseIdToken = await auth().currentUser.getIdToken(true);
       setUser(auth().currentUser);
       setFirebaseIdToken(firebaseIdToken);
       signUpMyServer(firebaseIdToken)
     } catch (error) {
       console.log('Error Message: ' + error.message);
     }
   }

     async function signUpMyServer(idToken) {
       try {
         const response = await fetch(`${httpsServer}/hello`, {
           headers: {Authorization: `Bearer ${idToken}`},
         });
         console.log(response.status);
         const responseJson = await response.json();
         console.log(responseJson);
       } catch (error) {
         console.log(error.message);
       }
     }

   async function handleSignOut() {
     try {
       await GoogleSignin.revokeAccess();
       await auth().signOut();
       setUser(null)
     } catch (error) {
       console.log(error.message);
     }
   }

  return (
    <AuthContext.Provider value={{isSignedIn: !!user, user, firebaseIdToken, handleGoogleSignIn, handleSignOut}}>
      {children}
    </AuthContext.Provider>
  );
};