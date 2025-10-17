import { useState } from "react";
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
						<Input variant="grande" type="text" value={descripcion} name="descripcion" onChange={e => setDescripcion(e.target.value)}>Descripción del estado del libro</Input>
						<div className="body-crear-prestamo__form__limite-dias">
						<Input type="number" value={limiteDias} name="limiteDias" onChange={e => setLimiteDias(e.target.value)}>Duración del préstamo</Input>
						</div>

						<Button type="submit" color="oscuro" variant="default" disabled={loading}>Crear publicación</Button>
					</form>
				)}

				{pagina == "social" &&(
					<p>Social</p>
				)}
				{/*{data && <p>{JSON.stringify(data, null, 2)}</p>}
				{error && <p>{error}</p>}*/}
				<img className="body-crear-prestamo__img" src={principitoLuna} alt="Principito en la Luna" />
			</main>
		</>
	)
};