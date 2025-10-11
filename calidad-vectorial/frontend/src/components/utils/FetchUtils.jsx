import { useState, useEffect } from "react";

export const useFetch = (url) => {
    const [data, setData] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const controller = new AbortController();
        const signal = controller.signal;

        const getData = async () => {
            setLoading(true);
            try {
                const respond = await fetch("http://localhost:8080/" + url, {
                    method: "GET",
                    credentials: "include",
                    signal
                });
                if (respond.ok) { 
                    const json = await respond.json();
                    setData(json);
                } else {
                    setError("Error en la respuesta del servidor.");
                }
            } catch (err) {
                if (err.name !== "AbortError") {
                    setError("Error: " + err.message);
                }
            } finally {
                setLoading(false);
            }
        };

        getData();

        return () => controller.abort();
    }, [url]);

    return { data, loading, error };
};

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