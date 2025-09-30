import React, { useState } from 'react';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "./../login/Utils";


export const Register = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirmation, setPasswordConfirmation] = useState("");
    const [message, setMessage] = useState("");
    
    const handleRegister = async (evento) => {
        evento.preventDefault();
        try {
            const respond = await fetch("http://localhost:8080/usuario/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password, passwordConfirmation }),
                credentials: "include"
            });
            if (respond.ok) {
                await loginRequest(username, password); 
                // Login automatico sin guardar respond      
                navigate("/login");
            } else {
                const text = await respond.text();
                setMessage(text);
            }
        } catch (error) {
            setMessage("Error de conexión");
        }
    } 

  return (
    <div>
        <form onSubmit={handleRegister}>
            <input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Usuario" />
            <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Contraseña" />
            <input type="password" value={passwordConfirmation} onChange={e => setPasswordConfirmation(e.target.value)} placeholder="Confirmar Contraseña" />
            <button type="submit">Registrarse</button>

            {message && <p>{message}</p>}
        </form>
        <Link to="/login">
            Tenes una cuenta? Inicia sesión
        </Link>
        
    </div>
  )
}