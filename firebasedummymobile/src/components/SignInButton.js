import React, { useContext } from 'react';
import {GoogleSigninButton} from '@react-native-community/google-signin';
import {AuthContext} from '../contexts/AuthContext';

export default SignInButton = () => {
  const {handleGoogleSignIn} = useContext(AuthContext);

  return (
    <GoogleSigninButton
      size={GoogleSigninButton.Size.Standard}
      color={GoogleSigninButton.Color.Light}
      onPress={handleGoogleSignIn}
    />
  );
};
