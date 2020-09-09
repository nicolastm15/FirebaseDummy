import React, {useContext} from 'react';

import {Button, View, Alert, ToastAndroid} from 'react-native';

import {serverUrl} from '../../serverUrl.json';
import {AuthContext} from '../contexts/AuthContext';
//here comes the name of your domain server: heroku, aws, gcs, or even your local machine
//I saved this address inside the file ./serverUrl.json
const httpsServer = 'http://192.168.0.109:8080';

export default HelloButton = ({style, role}) => {
  const {firebaseIdToken} = useContext(AuthContext);

  async function sayHello(firebaseIdToken) {
    console.log(firebaseIdToken);
    try {
      const response = await fetch(`${httpsServer}/${role}/hello`, {
        headers: {Authorization: `Bearer ${firebaseIdToken}`},
      });
      console.log(response.status);
      const responseJson = await response.json();
      ToastAndroid.show(responseJson.message,25);
    } catch (error) {
      console.log(error.message);
    }
  }
  return (
    <View style = {style}>
      <Button
        title = {`Say Hello to ${role} user`}
        onPress={() => sayHello(firebaseIdToken)}></Button>
    </View>
  );
};
