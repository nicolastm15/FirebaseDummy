import React, {useContext} from 'react';
import {Button, View} from 'react-native';
import {AuthContext} from '../contexts/AuthContext';

export default SignOutButton = ({style}) => {
  const {handleSignOut} = useContext(AuthContext);

  return (
    <View style={style}>
      <Button title="Logout" onPress={handleSignOut}></Button>
    </View>
  );
};
