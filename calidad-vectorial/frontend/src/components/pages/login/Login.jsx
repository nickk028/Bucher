import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "../../utils/LoginUtils";
import { Link } from "react-router-dom";
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
			<AuthBox titulo="Iniciar sesión" onSubmit={handleLogin}
				linkExtra={
						<Link to="/register">¿No tienes una cuenta? ¡Crea una!</Link>
					}>
				<Input type="text" value={username} name="username" onChange={e => setUsername(e.target.value)}>Nombre de usuario</Input>
				<Input type="password" value={password} name="password" onChange={e => setPassword(e.target.value)}>Contraseña</Input>
			</AuthBox>
		</div>
	);
}
export default Login;