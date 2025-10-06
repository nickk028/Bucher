import { postData } from "./FetchUtils";

export async function loginRequest({ username, password }, signal) {
    try {
        const response = await postData("auth/login", { username, password }, signal);
        return response;

    } catch (error) {
        return { ok: false, message: "Error de conexión" };
    }
}