import React, {useState, useEffect} from 'react';
import {View, Text, Button} from 'react-native';
import auth from '@react-native-firebase/auth';
import {
  GoogleSignin,
  GoogleSigninButton,
} from '@react-native-community/google-signin';

import {serverUrl} from './serverUrl.json';
import googleServicesCredentials from './android/app/google-services.json';

//here comes the name of your domain server: heroku, aws, gcs, or even your local machine
const httpsServer = 'http://192.168.0.109:8080';

//clientId do app relacionada com o google.
const {
  client_id: googleClientId
} = googleServicesCredentials.client[0].oauth_client[1];

GoogleSignin.configure({
  webClientId: googleClientId,
});

async function handleSignIn() {
  try {
    const response = await GoogleSignin.signIn();
    const {idToken: googleIdToken} = response;
    const googleCredential = auth.GoogleAuthProvider.credential(googleIdToken);
    await auth().signInWithCredential(googleCredential);
    const firebaseIdToken = await auth().currentUser.getIdToken(true);
    sayHello(firebaseIdToken);
  } catch (error) {
    console.log('Error Message: ' + error.message);
  }
  // const {token:firebaseIdToken} = await firebaseUser.getIdTokenResult();
}

async function handleSignOut() {
  try {
    await GoogleSignin.revokeAccess();
    await auth().signOut();
  } catch (error) {
    console.log(error.message);
  }
}

function sayHello(idToken) {
  fetch(`${httpsServer}/googlesignup`, {
    headers: {Authorization: `Bearer ${idToken}`},
  })
    .then((response) => response.json())
    .then((responseJson) => {
      console.log(responseJson.message);
    })
    .catch((error) => {
      console.log(error.message);
    });
}

export default function App() {
  // Set an initializing state whilst Firebase connects
  const [initializing, setInitializing] = useState(true);
  const [user, setUser] = useState();

  // Handle user state changes
  function onAuthStateChanged(user) {
    setUser(user);
    if (initializing) setInitializing(false);
  }

  useEffect(() => {
    const subscriber = auth().onAuthStateChanged(onAuthStateChanged);
    return subscriber; // unsubscribe on unmount
  }, []);

  if (initializing) return null;

  return !user ? (
    <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
      <GoogleSigninButton
        size={GoogleSigninButton.Size.Standard}
        color={GoogleSigninButton.Color.Light}
        onPress={handleSignIn}
      />
    </View>
  ) : (
    <View
      style={{
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      }}>
      <Text style={{marginBottom: 20}}>Welcome {user.email}</Text>
      <Button title="Logout" onPress={handleSignOut}></Button>
    </View>
  );
}
