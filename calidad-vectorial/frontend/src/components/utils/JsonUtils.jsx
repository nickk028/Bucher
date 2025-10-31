export const leerJSON = (key, fallback = null) => {
    const raw = localStorage.getItem(key);
    if (raw == null) return fallback;
    try {
        return JSON.parse(raw);
    } catch {
        return fallback;
    }
};

export const escribirJSON = (key, value) => {
    localStorage.setItem(key, JSON.stringify(value ?? {}));
};