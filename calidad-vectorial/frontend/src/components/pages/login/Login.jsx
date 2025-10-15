import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "../../utils/LoginUtils";
import { Link } from "react-router-dom";
import './Login.css';
import { AuthBox } from "../../elements/authbox/AuthBox";
import { Input } from "../../elements/input/Input";
import { Button } from "../../elements/buttons/Button";
import { LibroAnimado } from "../../elements/animatedbook/LibroAnimado";

export function Login() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const [ojoQueHabla, setOjoQueHabla] = useState(null);
	let [isDisabled, setIsDisabled] = useState(false);
	const navigate = useNavigate();

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

	const handleLogin = async (evento) => {
		evento.preventDefault();
		// Aborta la request previa si existe
		if (controllerRef.current) {
			controllerRef.current.abort();
			controllerRef.current = null;
		}
		// Nuevo controller para la nueva request
		const controller = new AbortController();
		controllerRef.current = controller;

		setError("");
		setOjoQueHabla(null);
		setIsDisabled(true);
		// Realiza la petición de login
		const respond = await loginRequest({username, password}, controller.signal);

		if (respond.ok) {
			navigate("/index");
		}
		else if (respond.message == "Error de conexión") {
			setError("Error de conexión");
			setOjoQueHabla(Math.floor(Math.random() * 3));
		} else {
			try {
				const jsonResponse = await respond.json();

				if (jsonResponse.errores) {
					const mensajesError = Object.values(jsonResponse.errores).join(". ");
					setError(mensajesError);
				}
				// Opción 2: Mostrar el título o detail
				else if (jsonResponse.title) {
					setError(jsonResponse.title);
				}
				// Opción 3: Mostrar el detail
				else if (jsonResponse.detail) {
					setError(jsonResponse.detail);
				}
				// Fallback: mostrar todo el JSON como string
				else {
					setError(JSON.stringify(jsonResponse));
				}

				setOjoQueHabla(Math.floor(Math.random() * 3));
			} catch (err) {
				// Si el fetch fue abortado no se muestra el error
				if (err.name !== "AbortError") {
					setError("Error: " + err.message);
					setOjoQueHabla(Math.floor(Math.random() * 3));
				}
			} finally {
				// limpia la referencia si sigue apuntando al controller actual
				if (controllerRef.current === controller) controllerRef.current = null;
				setIsDisabled(false);
			}
		}
	};

	return (
		<div className="body-login">
			<div className="body-login__grupo-ojos">
				<LibroAnimado variant="grande" color="rojo" mensaje={error} mostrarMensaje={ojoQueHabla === 0}>
					Bü
				</LibroAnimado>
				<LibroAnimado variant="chico" color="azul" mensaje={error} mostrarMensaje={ojoQueHabla === 1}>
					ch
				</LibroAnimado>
				<LibroAnimado variant="medio" color="amarillo" mensaje={error} mostrarMensaje={ojoQueHabla === 2}>
					er
				</LibroAnimado>
			</div>

			<AuthBox titulo="Iniciar sesión" onSubmit={handleLogin}
				botonDer={
					<Button type="submit" variant="default" color="oscuro" isDisabled={isDisabled}>Aceptar</Button>
					}
				linkExtra={
						<Link to="/register">¿No tienes una cuenta? ¡Crea una!</Link>
					}>
				<Input type="text" value={username} name="username" onChange={e => setUsername(e.target.value)}>Nombre de usuario</Input>
				<Input type="password" value={password} name="password" onChange={(e) => setPassword(e.target.value)}>Contraseña</Input>
			</AuthBox>
		</div>
	);
}