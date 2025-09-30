import React from 'react'

export const Index = () => {
    const handleIndex = async (evento) => {
        evento.preventDefault();
        try {
            const respond = await fetch("http://localhost:8080/publicacion", {
                method: "GET",
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
    const ControlClickLibro = (id) => {
        window.location.href = `http://localhost:8080/publicacion/${id}`;
    }
  return (
    <div>index</div>
  )
}
