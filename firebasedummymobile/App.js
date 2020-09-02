import React from 'react';
import Main from './src/components/Main';
import {AuthProvider} from './src/contexts/AuthContext';

export default function App() {
  return(
  <AuthProvider>
    <Main />
  </AuthProvider>)
}
