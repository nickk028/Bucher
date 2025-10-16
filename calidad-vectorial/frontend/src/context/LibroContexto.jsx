import { createContext, useContext, useState, useEffect } from "react";

const BookContext = createContext();

export const BookProvider = ({ children }) => {
    const [libroMensaje, setLibroMensaje] = useState(null);

    // 🔹 limpiar mensaje automáticamente luego de 4 s
    useEffect(() => {
        if (libroMensaje) {
            const timer = setTimeout(() => setLibroMensaje(null), 4000);
            return () => clearTimeout(timer); // limpia si el mensaje cambia antes
        }
    }, [libroMensaje]);
        return (
            <BookContext.Provider value={{ libroMensaje, setLibroMensaje }}>
                {children}
            </BookContext.Provider>
        );
    };
export const useBook = () => useContext(BookContext);