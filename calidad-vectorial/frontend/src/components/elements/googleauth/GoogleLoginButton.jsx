import { useEffect, useRef } from "react";
import { googleLoginRequest } from "../../utils/LoginUtils";

const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;

export const GoogleLoginButton = ({ onSuccess, onError }) => {
    const buttonRef = useRef(null);

    useEffect(() => {
        const handleCredentialResponse = async (credentialResponse) => {
            const respond = await googleLoginRequest(credentialResponse.credential);

            if (respond.ok) {
                onSuccess && onSuccess();
            } else {
                onError && onError(respond);
            }
        };

        const script = document.createElement("script");
        script.src = "https://accounts.google.com/gsi/client";
        script.async = true;
        script.onload = () => {
            window.google.accounts.id.initialize({
                client_id: GOOGLE_CLIENT_ID,
                callback: handleCredentialResponse
            });
            window.google.accounts.id.renderButton(buttonRef.current, { type: "icon" });
        };
        document.body.appendChild(script);

        return () => {
            document.body.removeChild(script);
        };
    }, [onSuccess, onError]);

    return <div ref={buttonRef}></div>;
};