import React from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import NavigationBar from './NavigationBar';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Roles } from './Roles';
import './Home.css';

const emptyUser = {
    username: null,
    role: null
}

const Home = () => {

    const [entries, setEntries] = useState([]);
    const [user, updateUser] = useState(emptyUser);

    const loggedIn = user !== emptyUser;
    const isAdmin = user.role === Roles.ADMIN.valueOf();

    const fetchEntries = () => {
        fetch('/entries')
            .then(response => response.json())
            .then(data => setEntries(data));
    }

    useEffect(() => {
        fetchEntries();
    }, []);

    useEffect(() => {
        const currentUser = localStorage.getItem('currentUser');
        if (currentUser !== null) {
            updateUser(JSON.parse(currentUser));
        }
    }, []);

    const remove = (id) => {
        fetch(`/entries/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then(() => {
            fetchEntries();
        });
    }

    const approve = (entry) => {
        entry.approved = true;

        fetch(`/entries/${entry.id}`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entry),
        }).then(() => {
            fetchEntries();
        });
    }

    const entryList = entries.map(entry => {
        const owner = loggedIn && entry.postedBy === user.username;

        if (entry.approved) {
            return <tr key={entry.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{entry.postedBy}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{entry.isImage ? <img src={entry.content} alt='your upload' /> : entry.content}</td>
                <td>
                    {(isAdmin || owner) && <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/entries/" + entry.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => remove(entry.id)}>Delete</Button>
                    </ButtonGroup>}
                </td>
            </tr>
        }
        else if (isAdmin || owner) {
            return <tr key={entry.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{owner ? entry.postedBy + ' [UNNAPROVED]' : entry.postedBy}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{entry.isImage ? <img src={entry.content} alt='your upload' /> : entry.content}</td>
                <td><ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/entries/" + entry.id}>Edit</Button>
                    {isAdmin && <Button size="sm" color="success" onClick={() => approve(entry)}>Approve</Button>}
                    <Button size="sm" color="danger" onClick={() => remove(entry.id)}>Delete</Button>
                </ButtonGroup>
                </td>
            </tr>
        }
        return null
    });

    const logout = () => {
        localStorage.removeItem('currentUser');
        updateUser(emptyUser);
    }

    return (
        <div>
            <NavigationBar />
            <Container fluid>
                <div className="float-right">
                    {loggedIn && <Button color="danger" onClick={logout}>Logout</Button>}
                    {!loggedIn && <Button color="primary" tag={Link} to="/login">Login</Button>}
                    <Button className="newEntryButton" color="success" tag={Link} to={loggedIn ? "/entries/new" : "/login"}>Add Entry</Button>
                </div>
                <h3>Guest Book</h3>
                <Table className="mt-4">
                    <thead>
                        <tr>
                            <th width="30%">Posted By</th>
                            <th width="50%">Entry</th>
                            <th width="20%"></th>
                        </tr>
                    </thead>
                    <tbody>
                        {entryList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
export default Home;