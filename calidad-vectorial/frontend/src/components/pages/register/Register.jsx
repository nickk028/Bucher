import React, { useState } from 'react';


export const Register = () => {
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
                const text = await respond.text();
                setMessage(text);
            } else {
                const text = await respond.text();
                setMessage(text);
            }
        } catch (error) {
            setMessage("Error de conexión");
        }
    } 

  return (
    <div>Register</div>
  )
}