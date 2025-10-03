import React from 'react'
import { Link } from "react-router-dom";
import '../global.css';
import './Button.css';

export const Button = ({ children, variant='solapa', color='claro', to, isDisabled }) => {
    if (to) {
    return (
        <Link to={to} className={`btn btn--${variant} btn--${color}`}>{children}</Link>
        );
    }
    return (
        <button type='submit' className={`btn btn--${variant} btn--${color}`} disabled={isDisabled}>{children}</button>
    )
}