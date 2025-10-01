export async function loginRequest(username, password) {
    try {
        const respond = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
            credentials: "include"
        });
        return respond;
    } catch (error) {
        return { ok: false, message: "Error de conexión" };
    }
}