import jwt_decode from 'jwt-decode';
import Cookies from 'js-cookie';
import { Route, useNavigate } from 'react-router-dom';

const isTokenValid = () => {
    const token = Cookies.get('JWT_TOKEN'); // JWT_TOKEN nombre de la cookie
    // Obtiene el token de las cookies 

    if (!token) {
        return false; // Valida si existe el token
    };
    try {
        const decoded = jwt_decode(token); // Decodifica el token
        const currentTime = Date.now() / 1000; // Tiempo actual en segundos
        return decoded.exp > currentTime; // Comprueba la expiracion del token
    } catch (error) {
        return false; 
    };
};
