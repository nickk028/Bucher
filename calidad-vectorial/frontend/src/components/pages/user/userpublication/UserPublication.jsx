import React from 'react'

export const PublicacionUsuario = () => {
    const handlePublicacionUsuario = async (evento) => {
        evento.preventDefault();
        try {
            const respond = await fetch("http://localhost:8080/publicacion/propias", {
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
  return (
    <div>publication</div>
  )
}
