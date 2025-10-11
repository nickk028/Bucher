import { useState, useEffect, useRef, useCallback } from "react";

export const useFetch = (url) => {
    const [data, setData] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const controller = new AbortController();
        const signal = controller.signal;

        const fetcher = async () => { 
            setLoading(true);
            try {
                const respond = await getData(url, signal);
                if (respond.ok) { 
                    const contentType = respond.headers.get("content-type");
                    if (contentType && contentType.includes("application/json")) {
                        const json = await respond.json();
                        setData(json);
                    } else {
                        const text = await respond.text();
                        setData(text);
                    }

                } else {
                    setError("Error en la respuesta del servidor: " + respond.statusText);
                }
            } catch (err) {
                if (err.name !== "AbortError") {
                    setError("Error: " + err.message);
                }
            } finally {
                setLoading(false);
            }
        };
        
        fetcher();

        return () => controller.abort();
    }, [url]);

    return { data, loading, error };
};



export function usePost(url) {
    const [data, setData] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    // Controller de la request activa
    const controllerRef = useRef(null);
    const isMountedRef = useRef(true);

    // Cleanup al desmontar: abortar la request activa
    useEffect(() => {
        isMountedRef.current = true;
        return () => {
            isMountedRef.current = false;
            if (controllerRef.current) controllerRef.current.abort();
        };
    }, []);

    const execute = useCallback(async (payload) => {
        if (controllerRef.current) {
            controllerRef.current.abort();
            controllerRef.current = null;
        }

        const controller = new AbortController();
        controllerRef.current = controller;

        if (isMountedRef.current) {
            setLoading(true);
            setError("");
        }

        try {
            const respond = await postData(url, payload, controller.signal);
            
            if (controller.signal.aborted || !isMountedRef.current) return;

            if (respond.ok) {
                const contentType = respond.headers.get("content-type");
                if (contentType && contentType.includes("application/json")) {

                    const json = await respond.json();
                    console.log("[usePost] json:", json);
                    if (isMountedRef.current) setData(json);
                } else {

                    const text = await respond.text();
                    console.log("[usePost] json:", text);
                    if (isMountedRef.current) setData(text);
                }
            } else {
                if (isMountedRef.current) setError("Error en la respuesta del servidor: " + respond.statusText);
                console.log("error", error);
            }
        } catch (err) {
            if (err.name !== "AbortError" && isMountedRef.current) {
                setError("Error: " + err.message);
            }
        } finally {
            if (isMountedRef.current) setLoading(false);
            controllerRef.current = null;
        }
    }, [url]);

    return { data, loading, error, execute };
}

export const postData = async (url, data, signal) => {
    const respond = await fetch("http://localhost:8080/" + url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
        credentials: "include",
        signal: signal
    });
    return respond;
}

export const getData = async (url, signal) => {
    const respond = await fetch("http://localhost:8080/" + url, {
        method: "GET",
        credentials: "include",
        signal: signal
    });
    return respond;
}