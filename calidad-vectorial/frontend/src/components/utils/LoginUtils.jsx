import { postData } from "./FetchUtils";

export async function loginRequest({ username, password }, signal) {
    try {
        const response = await postData("auth/login", { username, password }, signal);
        return response;

    } catch (error) {
        return { ok: false, message: "Error de conexión" };
    }
}

export function validarSeguridadPassword(password) {
    let puntosSeguridad = 0;
    const LARGOMINIMO = 16;

    if (password.length < LARGOMINIMO) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (!/[A-Z]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (!/[a-z]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (!/[0-9]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (!/[!@#$%^&+=]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }

    return puntosSeguridad;
    }
