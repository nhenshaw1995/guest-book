
import React from 'react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import NavigationBar from './NavigationBar';

const emptyAlert = {
    colour: '',
    message: ''
};

const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [alert, setAlert] = useState(emptyAlert);

    const displayAlert = alert !== emptyAlert;

    const handleChangeUsername = (event) => {
        const target = event.target;
        const value = target.value;
        setUsername(value);
    }

    const handleChangePassword = (event) => {
        const target = event.target;
        const value = target.value;
        setPassword(value);
    }

    const handleLogin = (event) => {
        event.preventDefault();

        const user = {
            'username': username,
            'password': password
        };

        fetch('/users', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user),
        }).then(response => response.json().then(json => {
            if (response.status === 200) {
                localStorage.setItem('currentUser', JSON.stringify(json));
                window.location.href = "/entries";
            }
            else {
                setAlert({ colour: 'danger', message: 'Could not log you in' });
            }
        }));

    }

    const handleCreateAccount = (event) => {
        event.preventDefault();

        if (username === '') {
            setAlert({ colour: 'danger', message: 'Please enter a Username' });
        }
        else if (password === '') {
            setAlert({ colour: 'danger', message: 'Please enter a Password' });
        }
        else {
            const user = {
                'username': username,
                'password': password
            };

            fetch('/users/new', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user),
            }).then(response => {
                if (response.status === 201) {
                    setAlert({ colour: 'success', message: 'Account created' });
                }
                else {
                    setAlert({ colour: 'danger', message: 'Could not log you in' });
                }
            });
        }
    }

    return <>
        <NavigationBar />
        <Container>
            <h2>Login</h2>
            <Form>
                <FormGroup>
                    <Label for="username">Username</Label>
                    <Input type="text" name="username" id="username" value={username}
                        onChange={handleChangeUsername} />
                    <Label for="password">Password</Label>
                    <Input type="password" name="password" id="password" value={password}
                        onChange={handleChangePassword} />
                </FormGroup>
                <FormGroup>
                    <Button color="success" type="submit" onClick={handleLogin}>Login</Button>{' '}
                    <Button color="primary" type="submit" onClick={handleCreateAccount}>Create Account</Button>{' '}
                    <Button color="secondary" tag={Link} to="/entries">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
        {displayAlert && <Alert color={alert.colour} isOpen={displayAlert} toggle={() => setAlert(emptyAlert)}>
            {alert.message}
        </Alert>}
    </>
}

export default Login;