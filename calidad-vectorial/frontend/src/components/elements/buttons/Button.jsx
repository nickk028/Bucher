import React from 'react'
import { Link } from "react-router-dom";
import '../global.css';
import './Button.css';

export const Button = ({ children, type="button", variant='solapa', color='claro', to, isDisabled=false, onClick }) => {
    if (to) {
    return (
        <Link to={to} className={`btn btn--${variant} btn--${color}`}>{children}</Link>
        );
    }
    return (
        <button type={type} className={`btn btn--${variant} btn--${color}`} disabled={isDisabled} onClick={onClick}>{children}</button>
    )
}