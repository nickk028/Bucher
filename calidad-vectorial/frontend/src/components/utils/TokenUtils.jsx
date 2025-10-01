import { Navigate, Outlet } from 'react-router-dom';
import React, { useEffect, useState } from 'react';

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

    useEffect(() => {
        const checkToken = async () => {
            const result = await isTokenValid();
            setValid(result);
        };
        checkToken();
    }, []);

    if (valid === null) {
        return <div style={{textAlign: 'center', marginTop: '2rem'}}>Verificando autenticación...</div>;
    }
    if (valid) {
        return <Outlet />;
    } else {
        return <>
            <Navigate to="/login" replace />
            <div style={{textAlign: 'center', marginTop: '2rem', color: 'red'}}>Redirigiendo a login...</div>
        </>;
    }
};

