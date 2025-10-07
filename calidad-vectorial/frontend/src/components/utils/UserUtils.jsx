import { getData } from './FetchUtils';

// Helper - DO NOT use React hooks here. Returns a plain object { message, error }.
export const fetchUsuario = async (signal) => {
    const result = { message: "", error: "" };
    try {
        const respond = await getData("usuario/propio", signal);
        if (!respond.ok) {
            result.message = await respond.text();
        } else {
            const data = await respond.json();
            result.message = data;
        }
    } catch (err) {
        if (err.name !== 'AbortError') {
            result.error = err.message;
        } else {
            result.error = 'aborted';
        }
    } finally {
        return result;
    }
}