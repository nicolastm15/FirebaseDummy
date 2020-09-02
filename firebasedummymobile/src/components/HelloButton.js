import React, {useContext} from 'react';

import {Button, View} from 'react-native';

import {serverUrl} from '../../serverUrl.json';
import {AuthContext} from '../contexts/AuthContext';
//here comes the name of your domain server: heroku, aws, gcs, or even your local machine
//I saved this address inside the file ./serverUrl.json
const httpsServer = 'http://192.168.0.109:8080';

export default HelloButton = ({style}) => {
  const {firebaseIdToken} = useContext(AuthContext);

  async function sayHello(firebaseIdToken) {
    console.log(firebaseIdToken);
    try {
      const response = await fetch(`${httpsServer}/hello`, {
        headers: {Authorization: `Bearer ${firebaseIdToken}`},
      });
      console.log(response.status);
      const responseJson = await response.json();
      console.log(responseJson);
    } catch (error) {
      console.log(error.message);
    }
  }
  return (
    <View style = {style}>
      <Button
        title="Say Hello"
        onPress={() => sayHello(firebaseIdToken)}></Button>
    </View>
  );
};
