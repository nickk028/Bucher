import React from 'react'

export const CrearPublicacion = () => {
    const [titulo, setTitulo] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [limiteDias, setLimiteDias] = useState("");

    const handleCrearPublicacion = async (e) => {
		e.preventDefault();
		try {
			const respond = await fetch("http://localhost:8080/publicacion/crear", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ titulo, descripcion, limiteDias }),
				credentials: "include"
			});

			if (res.ok) {
				onLogin(); // actualiza estado en App
				const text = await res.text();
				setMessage(text);
			} else {
				const text = await res.text();
				setMessage(text);
			}
		} catch (err) {
			setMessage("Error de conexión");
		}
	};
  return (
    <div>CrearPublicacion</div>
  )
}
