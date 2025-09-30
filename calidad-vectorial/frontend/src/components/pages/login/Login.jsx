import { useState } from "react";

function Login({ onLogin }) {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [message, setMessage] = useState("");

	const handleLogin = async (e) => {
		e.preventDefault();
		try {
			const respond = await fetch("http://localhost:8080/auth/login", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ username, password }),
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
		<form onSubmit={handleLogin}>
			<input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Usuario" />
			<input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Contraseña" />
			<button type="submit">Ingresar</button>
			{message && <p>{message}</p>}
		</form>
	);
}

export default Login;