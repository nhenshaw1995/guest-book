import React from 'react';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import NavigationBar from './NavigationBar';
import { Roles } from './Roles';

const imageFormats = ['.jpg', '.jpeg', '.png', '.gif', '.bmp'];

const EditEntry = (props) => {

    const newEntry = props.match.params.id === 'new';

    const id = newEntry ? '' : props.match.params.id;

    const [entryText, setEntryText] = useState(newEntry ? '' : 'loading...');
    const [entryImage, setEntryImage] = useState('');
    const [validImageURL, setValidImageURL] = useState(true);
    const [alert, setAlert] = useState('');

    const displayImage = entryImage !== '' && validImageURL;
    const displayAlert = alert !== '';

    useEffect(() => {
        if (!newEntry) {
            fetch(`/entries/${id}`).then(data => data.json()).then(json => {
                json.isImage ? setEntryImage(json.content) : setEntryText(json.content)
            });
        }
    }, [newEntry, id])

    useEffect(() => {
        if (imageFormats.filter(format => entryImage.endsWith(format)).length === 0) {
            setValidImageURL(false);
        }
        else {
            fetch(entryImage)
                .then(res => {
                    setValidImageURL(res.status === 200);
                });
        }
    }, [entryImage])

    const handleChangeText = (event) => {
        setEntryText(event.target.value);
        setEntryImage('');
    }

    const handleChangeImage = (event) => {
        setEntryText('');
        setEntryImage(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        if (entryText === '' && entryImage === '') {
            setAlert('Please enter either an entry or a valid image URL');
            return;
        }

        if (entryText === '' && !displayImage) {
            setAlert('Image URL is invalid');
            return;
        }

        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        const username = currentUser.username;
        const isAdmin = currentUser.role === Roles.ADMIN.valueOf();
        const isImage = entryText === '';
        const content = isImage ? entryImage : entryText;

        const entry = newEntry ?
            {
                'content': content,
                'isImage': isImage,
                'postedBy': username,
                'approved': isAdmin
            } : {
                'id': id,
                'content': content,
                'isImage': isImage,
                'postedBy': username,
                'approved': isAdmin
            };

        fetch('/entries' + (newEntry ? '' : '/' + id), {
            method: newEntry ? 'POST' : 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entry),
        });
        window.location.href = "/entries";
    }

    const title = <h2>{newEntry ? 'Add Entry' : 'Edit Entry'}</h2>;

    return <>
        <NavigationBar />
        <Container>
            {title}
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="entryText">Write an Entry</Label>
                    <Input type="text" name="entryText" id="entryText" value={entryText}
                        onChange={handleChangeText} />
                </FormGroup>
                <FormGroup>
                    <Label for="entryImage">Or enter an image URL</Label>
                    <Input type="text" name="entryImage" id="entryImage" value={entryImage}
                        onChange={handleChangeImage} />
                    <br />
                </FormGroup>
                {displayImage && <div>
                    <img src={entryImage} alt='your upload' />
                    <br />
                    <br />
                </div>}
                <FormGroup>
                    <Button color="success" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/entries">Cancel</Button>
                </FormGroup>
            </Form>
            {displayAlert && <Alert color='danger' isOpen={displayAlert} toggle={() => setAlert('')}>
                {alert}
            </Alert>}
        </Container>
    </>
}

export default EditEntry;