import { useState, useEffect, useRef } from "react";
import { postData } from "../../utils/FetchUtils";

export const CrearPublicacion = () => {
    const [titulo, setTitulo] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [limiteDias, setLimiteDias] = useState("");
	const [message, setMessage] = useState("");
	const [error, setError] = useState("");
	let [isDisabled, setIsDisabled] = useState(false);
	// Referencia al controller de la request activa
	const controllerRef = useRef(null);

	// cleanup: abort request activo si el componente se desmonta
	useEffect(() => {
		return () => {
			if (controllerRef.current) { 
				controllerRef.current.abort()
			} 
		};
	}, []);

    const handleCrearPublicacion = async (evento) => {
		evento.preventDefault();
		// Aborta la request previa si existe
		if (controllerRef.current) {
			controllerRef.current.abort();
			controllerRef.current = null;
		}
		// Nuevo controller para la nueva request
		const controller = new AbortController();
		controllerRef.current = controller; // guarda el controller en la referencia

		setMessage("");
		setError("");
		setIsDisabled(true);
		try {
			const respond = await postData("publicacion/crear", 
				{ titulo, descripcion, limiteDias: parseInt(limiteDias) }, 
				controller.signal);
			if (respond.ok) {
				const text = await respond.text();
				setMessage(text);
			} else {
				const text = await respond.text();
				setError(text);
			}
		} catch (err) {
			if (err.name !== 'AbortError') {
				setError("Error de conexión: " + err.message);
			}	
		} finally {
			setIsDisabled(false);
		}
	};
	return (
		<div>
			<h1>Crear Publicacion</h1>
			<form onSubmit={handleCrearPublicacion}>
				<input type="text" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Título" />
				<input type="text" value={descripcion} onChange={e => setDescripcion(e.target.value)} placeholder="Descripción" />
				<input type="number" value={limiteDias} onChange={e => setLimiteDias(e.target.value)} placeholder="Límite de Días" />
				<button type="submit" disabled={isDisabled}>Crear Publicación</button>

				{message && <p>{message}</p>}
				{error && <p>{error}</p>}
			</form>
		</div>
	)
}
