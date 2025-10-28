import { createContext } from "react";
import { BookProvider } from "./BookContext";

export const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
    return (
        <GlobalContext.Provider value={{}}>
            <BookProvider>
                {children}
            </BookProvider>
        </GlobalContext.Provider>
    );
};
