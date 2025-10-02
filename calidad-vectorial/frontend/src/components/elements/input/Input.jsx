import React from 'react'
import '../../elements/global.css';
import './Input.css';

export const Input = ({ children, type="text", name }) => {
    return (
        <div className='input-group'>
            <label className='input-group__label' htmlFor={name}>{children}</label>
            <input className='input-group__input' name={name} id={name} type={type} required></input>
            
        </div>
    )
}