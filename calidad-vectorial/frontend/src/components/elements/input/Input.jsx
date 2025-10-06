import { useState } from 'react'
import '../../elements/global.css';
import './Input.css';
import ver from '../../../assets/img/ver.png';
import esconder from '../../../assets/img/esconder.png';

export const Input = ({ children, type, name, value, onChange, onFocus, onBlur }) => {
    const [showPassword, setShowPassword] = useState(false);

    const togglePassword = (e) => {
        e.preventDefault();
        setShowPassword((inputType) => !inputType);
    };

    const inputType = type == "password" && showPassword ? "text" : type;
    return (
        <div className='input-group'>
            <input className={`input-group__input input-group__input--${type}`} onChange={onChange} onFocus={onFocus} onBlur={onBlur} name={name} id={name} type={inputType} value={value} required />
            <label className='input-group__label' htmlFor={name}> {children} </label>

            {type === "password" && (
                <button type="button" className="input-group__ojo" onMouseDown={togglePassword}>
                    {showPassword ? (
                        <img src={esconder} alt="esconder" />
                    ): (
                        <img src={ver} alt="ver" />
                    )}
                </button>
            )}
        </div>
    );
};