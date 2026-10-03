import { useMemo, useState, useEffect } from 'react';
import { LibroCard } from '../../elements/book/LibroCard';
import { useFetch } from '../../utils/FetchUtils';
import { UsuarioBiblioteca } from '../../elements/user/data/UsuarioBiblioteca';
import './Biblioteca.css';

export const Biblioteca = () => {
    return (
        <main className="biblioteca">
            <header className="biblioteca__encabezado">
                <h1>Mi biblioteca</h1>
                <p>LLeva registro de tus lecturas y libros</p>
            </header>

            <section className="biblioteca__contenedor">
                <UsuarioBiblioteca />
            </section>
        </main>
    );
};
