import { useEffect, useRef } from 'react';
import { googleLoginRequest } from '../../utils/LoginUtils';
import './GoogleLoginButton.css';

const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;

export const GoogleLoginButton = ({ onSuccess, onError }) => {
    const wrapperRef = useRef(null);
    const googleBtnRef = useRef(null);

    // Guardamos los callbacks en un ref para no reinicializar Google en cada render
    const callbacks = useRef({ onSuccess, onError });
    callbacks.current = { onSuccess, onError };

    useEffect(() => {
        const init = () => {
            window.google.accounts.id.initialize({
                client_id: GOOGLE_CLIENT_ID,
                callback: async ({ credential }) => {
                    const respond = await googleLoginRequest(credential);
                    if (respond.ok) {
                        callbacks.current.onSuccess?.();
                    } else {
                        callbacks.current.onError?.(respond);
                    }
                },
            });

            window.google.accounts.id.renderButton(googleBtnRef.current, {
                type: 'standard',
                // El botón de Google debe cubrir todo tu botón (máx. 400px)
                width: Math.min(wrapperRef.current.offsetWidth, 400),
            });
        };

        // Si el script ya está cargado, no lo volvemos a agregar
        if (window.google?.accounts?.id) {
            init();
            return;
        }

        const script = document.createElement('script');
        script.src = 'https://accounts.google.com/gsi/client';
        script.async = true;
        script.onload = init;
        document.body.appendChild(script);
    }, []);

    return (
        <div className="google-login" ref={wrapperRef}>
            <button type="button" className="google-login__btn" tabIndex={-1}>
                <img src="/assets/img/logos/googleLogo.png" alt="" width="18" height="18" />
                Iniciar sesión con Google
            </button>

            <div className="google-login__overlay" ref={googleBtnRef} />
        </div>
    );
};