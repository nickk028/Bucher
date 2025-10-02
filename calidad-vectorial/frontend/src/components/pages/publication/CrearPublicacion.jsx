import React from 'react'
import { useState } from "react";

export const CrearPublicacion = () => {
    const [titulo, setTitulo] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [limiteDias, setLimiteDias] = useState("");
	const [message, setMessage] = useState("");

    const handleCrearPublicacion = async (e) => {
		e.preventDefault();
		try {
			const respond = await fetch("http://localhost:8080/publicacion/crear", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ titulo, descripcion, limiteDias }),
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
	};
	return (
		<div>
			<h1>Crear Publicacion</h1>
			<form onSubmit={handleCrearPublicacion}>
				<input type="text" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Título" />
				<input type="text" value={descripcion} onChange={e => setDescripcion(e.target.value)} placeholder="Descripción" />
				<input type="number" value={limiteDias} onChange={e => setLimiteDias(e.target.value)} placeholder="Límite de Días" />
				<button type="submit">Crear Publicación</button>

				{message && <p>{message}</p>}
			</form>
		</div>
	)
}
