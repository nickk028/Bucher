import { useState, useEffect, useRef } from "react";
import { usePost } from '../../utils/FetchUtils';
import { Button } from '../../elements/buttons/Button';
import { ButtonGroup } from '../../elements/buttons/ButtonGroup';
import { Input } from '../../elements/input/Input';
import principitoLuna from '../../../assets/img/principitoLuna.png'
import './CrearPublicacion.css';

export const CrearPublicacion = () => {
    const [titulo, setTitulo] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [limiteDias, setLimiteDias] = useState("");

	const [pagina, setPagina] = useState("prestamo");

    const { data, loading, error, execute } = usePost("publicacion/crear");

    const handleCrearPublicacion = async (e) => {
        e.preventDefault();
        await execute({ titulo, descripcion, limiteDias: parseInt(limiteDias) });
    };

	return (
		<>
			<nav>
				<ButtonGroup>
					<Button color={pagina == "social" ? "oscuro" : "claro"} onClick={pagina !="social" ? () => setPagina("social") : undefined}>Social</Button>
					<Button color={pagina == "prestamo" ? "oscuro" : "claro"} onClick={pagina != "prestamo" ? () => setPagina("prestamo") : undefined}>Préstamo</Button>
				</ButtonGroup>
			</nav>
			<main className="body-crear-prestamo">
				{pagina == "prestamo" &&(
					<form className="body-crear-prestamo__form" onSubmit={handleCrearPublicacion}>
						<Input type="text" value={titulo} name="titulo" onChange={e => setTitulo(e.target.value)}>Título</Input>
						<Input type="text" value={descripcion} name="descripcion" onChange={e => setDescripcion(e.target.value)}>Descripción</Input>
						<Input type="number" value={limiteDias} name="limiteDias" onChange={e => setLimiteDias(e.target.value)}>Duración del préstamo</Input>

						<Button type="submit" color="oscuro" variant="default" disabled={loading}>Crear publicación</Button>
					</form>
				)}

				{pagina == "social" &&(
					<p>s</p>
				)}

				<img className="body-crear-prestamo__img" src={principitoLuna} alt="Principito en la Luna" />
			</main>
		</>
	)

	{/*<div>
		<h1>Crear Publicacion</h1>
		<form onSubmit={handleCrearPublicacion}>
			<input type="text" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Título" />
			<input type="text" value={descripcion} onChange={e => setDescripcion(e.target.value)} placeholder="Descripción" />
			<input type="number" value={limiteDias} onChange={e => setLimiteDias(e.target.value)} placeholder="Límite de Días" />
			<button type="submit" disabled={loading}>Crear Publicación</button>

			{data && <p>{JSON.stringify(data, null, 2)}</p>}
			{error && <p>{error}</p>}
		</form>
	</div>*/}
};