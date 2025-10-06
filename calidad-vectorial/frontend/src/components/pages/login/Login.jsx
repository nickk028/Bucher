import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "../../utils/LoginUtils";
import { Link } from "react-router-dom";
import './Login.css';
import { AuthBox } from "../../elements/authbok/AuthBox";
import { Input } from "../../elements/input/Input";
import { Button } from "../../elements/buttons/Button";
import { OjosAnimados } from "../../elements/ojos/OjosAnimados";

function Login() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [message, setMessage] = useState("");
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
		controllerRef.current = controller; // guarda el controller en la referencia

		setMessage("");
		setIsDisabled(true);
		// Realiza la petición de login
		const respond = await loginRequest({username, password}, controller.signal);

		if (respond.ok) {
			navigate("/index");
		} 
		else if (respond.message == "Error de conexión") {
			setMessage("Error de conexión");
		} else {
			try {
				const text = await respond.text();
				setMessage(text);
			} catch (error) {
				// Si el fetch fue abortado no se muestra el error
				if (error.name !== "AbortError") {
					setMessage("Error: " + error.message);
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
			<OjosAnimados mensajeError={message} />

			<AuthBox titulo="Iniciar sesión" onSubmit={handleLogin}
			
				botonDer={
					<Button type="submit" variant="default" color="oscuro" isDisabled={isDisabled}>Aceptar</Button>
					}
				linkExtra={
						<Link to="/register">¿No tienes una cuenta? ¡Crea una!</Link>
					}>
				<Input type="text" value={username} name="username" onChange={e => setUsername(e.target.value)}>Nombre de usuario</Input>
				<Input type="password" value={password} name="password" onChange={(e) => setPassword(e.target.value)} onFocus={() => window.dispatchEvent(new Event("passwordFocus"))} onBlur={() => window.dispatchEvent(new Event("passwordBlur"))}>Contraseña</Input>

			</AuthBox>
		</div>
	);
}
export default Login;