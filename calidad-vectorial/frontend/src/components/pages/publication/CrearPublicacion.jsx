import { useState, useEffect, useRef } from "react";
import { usePost } from '../../utils/FetchUtils';

export const CrearPublicacion = () => {
    const [titulo, setTitulo] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [limiteDias, setLimiteDias] = useState("");

    const { data, loading, error, execute } = usePost("publicacion/crear");

    const handleCrearPublicacion = async (e) => {
        e.preventDefault();
        await execute({ titulo, descripcion, limiteDias: parseInt(limiteDias) });
    };

	return (
		<div>
			<h1>Crear Publicacion</h1>
			<form onSubmit={handleCrearPublicacion}>
				<input type="text" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Título" />
				<input type="text" value={descripcion} onChange={e => setDescripcion(e.target.value)} placeholder="Descripción" />
				<input type="number" value={limiteDias} onChange={e => setLimiteDias(e.target.value)} placeholder="Límite de Días" />
				<button type="submit" disabled={loading}>Crear Publicación</button>

				{data && <p>{JSON.stringify(data, null, 2)}</p>}
				{error && <p>{error}</p>}
			</form>
		</div>
	)
}
