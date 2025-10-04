import "./FetchUtils";
import { fetchData } from "./FetchUtils";

export async function loginRequest(username, password, signal) {
    try {
        fetchData()
        
    } catch (error) {
        return { ok: false, message: "Error de conexión" };
    }
}