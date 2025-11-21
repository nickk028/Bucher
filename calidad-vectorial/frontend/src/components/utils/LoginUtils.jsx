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
    const LARGO_MINIMO = 16;
    const LARGO_MAXIMO = 60;

    if (password.length >= LARGO_MINIMO && password.length <= LARGO_MAXIMO) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (/[A-Z]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (/[a-z]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (/[0-9]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    if (/[!@#$%^&+=]/.test(password)) {
        puntosSeguridad = puntosSeguridad + 1;
    }
    return puntosSeguridad;
};