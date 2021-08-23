import React from 'react';
import { Navbar, NavbarBrand } from 'reactstrap';
import { Link } from 'react-router-dom';

const NavigationBar = () => {

    return (<>
        <Navbar color="dark" dark expand="md" >
            <NavbarBrand tag={Link} to="/entries">Home</NavbarBrand>
        </Navbar>
        <br />
    </>)
}

export default NavigationBar;