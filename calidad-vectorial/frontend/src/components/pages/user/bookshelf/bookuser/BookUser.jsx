import React from 'react'

export const BookUser = () => {
    const handleBookUser = async (evento) => {
        evento.preventDefault();
        try {
            const respond = await fetch("http://localhost:8080/biblioteca/${id}", {
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
    <div>bookuser</div>
  )
}
