import React, { useState } from 'react';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "../../utils/LoginUtils";
import './Register.css';
import { AuthBox } from "../../elements/authbok/AuthBox";
import { Input } from "../../elements/input/Input";


export const Register = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [verificationPassword, setVerificationPassword] = useState("");
    const [message, setMessage] = useState("");
    
    const handleRegister = async (evento) => {
        evento.preventDefault();
        try {
            const respond = await fetch("http://localhost:8080/usuario/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password, verificationPassword }),
                credentials: "include"
            });
            if (respond.ok) {
                await loginRequest(username, password);
                // Login automatico sin guardar respond
                navigate("/index");
            } else {
                const text = await respond.text();
                setMessage(text);
            }
        } catch (error) {
            setMessage("Error de conexión");
        }
    };

    return (
        <div className="body-register">
			<AuthBox titulo="Registrarse" onSubmit={handleRegister} isDisabled={true}
                linkExtra={
                    <Link to="/login">¿Ya tienes una cuenta? ¡Inicia sesión!</Link>
                }>
				<Input type="text" value={username} name="username" onChange={e => setUsername(e.target.value)}>Nombre de usuario</Input>
				<Input type="password" value={password} name="password" onChange={e => setPassword(e.target.value)}>Contraseña</Input>
                <Input type="password" value={verificationPassword} name="verificationPassword" onChange={e => setVerificationPassword(e.target.value)}>Confirmar contraseña</Input>
			</AuthBox>
		</div>
    );
}