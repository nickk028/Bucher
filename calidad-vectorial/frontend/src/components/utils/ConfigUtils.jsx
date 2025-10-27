import { leerJSON, escribirJSON } from './JsonUtils';

// La configuracion por defecto
export const DEFAULT = {
    buchy: true,
    colorBuchy: "amarillo"
};

export const validarExisteConfig = () => {
    const existente = leerJSON("Configuracion", null);
    if (existente == null) {
        escribirJSON("Configuracion", DEFAULT);
        return { ...DEFAULT };
    }

    // merge para asegurar claves nuevas en el tiempo
    const merged = { ...DEFAULT, ...existente };
    if (JSON.stringify(merged) !== JSON.stringify(existente)) {
        escribirJSON("Configuracion", merged);
    }
    return merged;
};

export const getConfig = () => {
    const existente = leerJSON("Configuracion", null);
    // Si no existe devuelve la config por defecto
    if (existente == null) return { ...DEFAULT }; 
    // Si existe devuelve un merge de ambas
    return { ...DEFAULT, ...existente };
};

/**
* setConfig: guarda estado de configuración.
* - Acepta objeto parcial: { clave: valor }
* - O una función (vieja) => nueva
* Retorna el estado final escrito.
*/
export const setConfig = (partialOrUpdater) => {
    const vieja = getConfig();
    const nueva = typeof partialOrUpdater === 'function'
        ? (partialOrUpdater(vieja) ?? vieja)
        : { ...vieja, ...(partialOrUpdater ?? {}) };

    escribirJSON("Configuracion", nueva);
    return nueva;
};