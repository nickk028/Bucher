import { useState, useRef } from "react"
import ver from "../../../assets/img/icons/utils/ver.png";
import esconder from "../../../assets/img/icons/utils/esconder.png";
import "./InputLibro.css"

export const InputLibro = ({ children, variant, type, ...props }) => {
    const [showPassword, setShowPassword] = useState(false);
        const inputRef = useRef(null);
    
        const togglePassword = (e) => {
            e.preventDefault();
            setShowPassword((inputType) => !inputType);
    
            if (inputRef.current) {
                // Selecciona el input si se toca el ojo
                inputRef.current.focus();
            }

                if (inputType === "password") {
                    // Chusmea la contraseña
                    window.dispatchEvent(new Event("passwordPeek"));
                }
                if (inputType === "text") {
                    // Cierra los ojos
                    window.dispatchEvent(new Event("passwordFocus"));
                }
        };

        const inputType = type == "password" && showPassword ? "text" : type;

    return (
        <div className="input-group--libro">
            <label className="input-group__label--libro" htmlFor={name}>{children}</label>
            <input className={`input-group__input--libro input-group__input--libro--${type}`}
                {...props}
                onFocus={() => {
                if (type == "password") {if (inputType === "text") {window.dispatchEvent(new Event("passwordPeek"));}
                else {window.dispatchEvent(new Event("passwordFocus"))};}
                else {onFocus && onFocus()}
            }}
                onBlur={ () => {
                    if (type == "password") {window.dispatchEvent(new Event("passwordBlur"))}
                    else {onBlur && onBlur()}}}
                type={inputType}
            />

            {type === "password" && (
                <button type="button" className="input-group__ojo" onMouseDown={togglePassword}  >
                    {showPassword ? (
                        <img src={ver} alt="esconder" />
                    ): (
                        <img src={esconder} alt="ver" />
                    )}
                </button>
            )}
        </div>
    )
}