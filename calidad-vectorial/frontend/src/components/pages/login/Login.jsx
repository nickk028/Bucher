import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "../../utils/LoginUtils";
import './Login.css';
import { AuthBox } from "../../elements/authbok/AuthBox";
import { Input } from "../../elements/input/Input";

function Login() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [message, setMessage] = useState("");
	const navigate = useNavigate();

	const handleLogin = async (evento) => {
		evento.preventDefault();
		const respond = await loginRequest(username, password);

		if (respond.ok) {
			navigate("/index");
		} else {
			try {
				const text = await respond.text();
				setMessage(text);
			} catch (error) {
				setMessage("Error de conexion");
			}
		}
	};

	return (
		<div className="body-login">
			<AuthBox title="Iniciar sesión">
				<form className="body-login__form" onSubmit={handleLogin}>
					<Input type="text" name="username">Nombre de usuario</Input>
					<Input type="password" name="password">Contraseña</Input>
				</form>
			</AuthBox>
		</div>

		/*
		<div>
			<form onSubmit={handleLogin}>
				<input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Usuario" />
				<input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Contraseña" />
				<button type="submit">Ingresar</button>
				
				{message && <p>{message}</p>}
			</form>
			<Link to="/register">
				No tenes una cuenta? Registrate
			</Link>
		</div>*/
	);
}

export default Login;