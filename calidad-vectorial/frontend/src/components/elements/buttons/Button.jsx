import React from 'react'
import '../global.css';
import './Button.css';

export const Button = ({ children, variant='solapa', color='claro' }) => {
    return (
        <button className={`btn btn--${variant} btn--${color}`}>{children}</button>
    )
}