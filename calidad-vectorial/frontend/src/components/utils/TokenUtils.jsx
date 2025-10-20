import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useEffect, useState  } from 'react';
import { Layout } from "../../layouts/Layout"

const isTokenValid = async () => {
    try {
        const respond = await fetch('http://localhost:8080/auth/validar-token', {
            method: 'GET',
            credentials: 'include',
            cache: 'no-store'
        });
        if (respond.ok) {
            return true;
        } else {
            console.log("Token inválido");
            return false;
        }
    } catch (error) {
        console.log("Error al validar token");
        return false;
    }
};

export const ProtectedRoute = () => {
    const [valid, setValid] = useState(null);
    const url = useLocation();

    useEffect(() => {
        const checkToken = async () => {
            const result = await isTokenValid();
            setValid(result);
        };
        checkToken();
    }, [url.pathname]);

    if (valid === null) {
        return <div style={{textAlign: 'center', marginTop: '2rem'}}>Verificando autenticación...</div>;
    }
    if (valid) {
        if (url.pathname === "/login" || url.pathname === "/register") {
            return <Navigate to="/index" replace />;
        } else {
            return <Layout />;
        }
    } else {
        if (url.pathname !== "/login" && url.pathname !== "/register") {
            return <Navigate to="/login" replace />;
        } else {
            return <Outlet />;
        }
    }
};

