import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EditEntry from "./EditEntry";
import Login from './Login';

function App() {
  return (
    <Router>
      <Switch>
        <Route path='/entries' exact={true} component={Home} />
        <Route path='/entries/:id' component={EditEntry} />
        <Route path='/login' component={Login} />
      </Switch>
    </Router>
  )
}

export default App;