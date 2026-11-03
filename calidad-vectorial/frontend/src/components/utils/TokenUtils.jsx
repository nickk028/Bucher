import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { createContext, useContext, useEffect, useState } from 'react';
import { Layout } from '../../layouts/Layout';
import { AdminLayout } from '../../layouts/AdminLayout';

const AuthContext = createContext({ roles: [], username: null });
export const useAuth = () => useContext(AuthContext);

const isTokenValid = async () => {
    try {
        const respond = await fetch('http://localhost:8080/auth/validar-token', {
            method: 'GET',
            credentials: 'include',
            cache: 'no-store',
        });
        if (respond.ok) {
            return respond.json();
        } else {
            console.log('Token inválido');
            return null;
        }
    } catch (error) {
        console.log('Error al validar token');
        return null;
    }
};

const esAdmin = (roles) => roles.includes('ADMIN');

export const ProtectedRoute = () => {
    const [valid, setValid] = useState(undefined);
    const url = useLocation();

    useEffect(() => {
        const checkToken = async () => {
            const result = await isTokenValid();
            setValid(result);
        };
        checkToken();
    }, [url.pathname]);

    if (valid === undefined) {
        return (
            <div style={{ textAlign: 'center', marginTop: '2rem' }}>
                Verificando autenticación...
            </div>
        );
    }

    const estaEnAuth = url.pathname === '/login' || url.pathname === '/register';

    if (!valid) {
        return estaEnAuth ? <Outlet /> : <Navigate to="/login" replace />;
    }

    if (estaEnAuth) {
        return <Navigate to={esAdmin(valid.roles) ? '/admin' : '/index'} replace />;
    }

    return (
        <AuthContext.Provider value={valid}>
            {esAdmin(valid.roles) ? <AdminLayout /> : <Layout />}
        </AuthContext.Provider>
    );
};

export const AdminRoute = () => {
    const { roles } = useAuth();
    return esAdmin(roles) ? <Outlet /> : <NotFound />;
};