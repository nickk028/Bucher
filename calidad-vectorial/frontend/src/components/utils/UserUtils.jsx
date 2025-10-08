import { getData } from './FetchUtils';

export const fetchUsuario = async (signal) => {
    const result = { message: "", error: "" };
    try {
        const respond = await getData("usuario/propio", signal);
        if (respond.ok) {
            result.message = await respond.json();   
        } else {
            const text = await respond.text();
            result.error = text;
        }
    } catch (err) {
        if (err.name === "AbortError") {
            result.error = "abortado";
        } else {
            result.error = "Hubo un error: " + err.message;
        }
    } finally {
        return result;
    }
}