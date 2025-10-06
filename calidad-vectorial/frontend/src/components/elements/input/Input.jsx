import React from 'react'
import '../../elements/global.css';
import './Input.css';



export const Input = ({ children, type, name, value, onChange, onFocus, onBlur }) => {
    return (
        <div className='input-group'>
            <input className='input-group__input' onChange={onChange} onFocus={onFocus} onBlur={onBlur} name={name} id={name} type={type} value={value} required />
            <label className='input-group__label' htmlFor={name}> {children} </label>
        </div>
    );
};
