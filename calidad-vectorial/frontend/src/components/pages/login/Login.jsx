import { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "./Utils";

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
			const text = await respond.text();
			setMessage(text);
		}
	};

	return (
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
		</div>
	);
}

export default Login;