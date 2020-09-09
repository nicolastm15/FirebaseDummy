import React, {useContext} from 'react';
import {View, Text, Button} from 'react-native';
import SignOutButton from './SignOutButton';
import SignInButton from './SignInButton';
import HelloButton from './HelloButton';
import {AuthContext} from '../contexts/AuthContext';

export default Main = () => {
  const {isSignedIn, user} = useContext(AuthContext);

  return !isSignedIn ? (
    <View
      style={{
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      }}>
      <SignInButton />
    </View>
  ) : (
    <View
      style={{
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      }}>
      <Text style={{marginBottom: 20}}>Welcome {user.email}</Text>
      <HelloButton style={{marginBottom: 20}} role = "admin"></HelloButton>
      <HelloButton style={{marginBottom: 20}} role = "basic"></HelloButton>
      <SignOutButton></SignOutButton>
    </View>
  );
};
