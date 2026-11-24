import { useEffect, useRef, useState } from 'react';
import { useFetch, usePost } from '../../utils/FetchUtils';
import { LibroCard } from '../../elements/book/LibroCard';
import { ComentarioSocial } from '../../elements/social/ComentarioSocial';
import { Button } from '../../elements/buttons/Button';
import { AutoCompletarLibro } from '../../elements/autocomplete/types/AutoCompletarLibro';

import MencionarUsuarioPosteo from '../../../assets/img/icons/posteo/mencionarUsuarioPosteo.svg?react';
import MencionarLibroPosteo from '../../../assets/img/icons/posteo/mencionarLibroPosteo.svg?react';
import SubirImagenPosteo from '../../../assets/img/icons/posteo/subirImagenPosteo.svg?react';
import ColorLetra from '../../../assets/img/icons/posteo/colorLetra.svg?react';
import NegritaPosteo from '../../../assets/img/icons/posteo/negritaPosteo.svg?react';
import SubrayarPosteo from '../../../assets/img/icons/posteo/subrayarPosteo.svg?react';
import TacharPosteo from '../../../assets/img/icons/posteo/tacharPosteo.svg?react';

import './ComentariosSocial.css';

const PATRON_ETIQUETA = /(?:^|\s)@([\p{L}\p{N}_.-]*[\p{L}\p{N}_-])/gu;

const extraerEtiquetas = (texto) => [
    ...new Set([...texto.matchAll(PATRON_ETIQUETA)].map((coincidencia) => coincidencia[1])),
];

export const ComentariosSocial = () => {
    const { data: dataUsuario } = useFetch('usuario/propio');
    const {
        data: dataPosteos,
        loading: loadingPosteos,
        error: errorPosteos,
        refetch: refetchPosteos,
    } = useFetch('publicacionSocial');
    const {
        data: dataCrear,
        loading: loadingCrear,
        error: errorCrear,
        execute: executeCrear,
    } = usePost('publicacionSocial/crear');

    const [texto, setTexto] = useState('');
    const [contieneSpoilers, setContieneSpoilers] = useState(false);
    const [mostrarBuscadorLibro, setMostrarBuscadorLibro] = useState(false);
    const [busquedaLibro, setBusquedaLibro] = useState('');
    const [libroSeleccionado, setLibroSeleccionado] = useState(null);

    const textareaRef = useRef(null);
    const buscadorLibroRef = useRef(null);

    useEffect(() => {
        if (mostrarBuscadorLibro) {
            buscadorLibroRef.current?.querySelector('input')?.focus();
        }
    }, [mostrarBuscadorLibro]);

    useEffect(() => {
        if (dataCrear) {
            setTexto('');
            setContieneSpoilers(false);
            setLibroSeleccionado(null);
            refetchPosteos();
        }
    }, [dataCrear]);

    const handleMencionarUsuario = () => {
        const inicio = textareaRef.current.selectionStart;
        const fin = textareaRef.current.selectionEnd;
        const necesitaEspacio = inicio > 0 && !/\s/.test(texto[inicio - 1]);
        const insercion = (necesitaEspacio ? ' ' : '') + '@';
        const posicionCursor = inicio + insercion.length;

        setTexto(texto.slice(0, inicio) + insercion + texto.slice(fin));

        requestAnimationFrame(() => {
            textareaRef.current.focus();
            textareaRef.current.setSelectionRange(posicionCursor, posicionCursor);
        });
    };

    const handleAlternarBuscadorLibro = () => {
        setBusquedaLibro('');
        setMostrarBuscadorLibro((actual) => !actual);
    };

    const handleSeleccionarLibro = (libro) => {
        setLibroSeleccionado(libro);
        setBusquedaLibro('');
        setMostrarBuscadorLibro(false);
    };

    const handleTeclaBuscadorLibro = (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
        }
        if (e.key === 'Escape') {
            setBusquedaLibro('');
            setMostrarBuscadorLibro(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        await executeCrear({
            textoPublicacion: texto.trim(),
            contieneSpoilers,
            libroId: libroSeleccionado?.id ?? null,
            etiquetas: extraerEtiquetas(texto),
        });
    };

    return (
        <main className="body-comsocial">
            <form className="body-comsocial__form" onSubmit={handleSubmit}>
                <div className="body-comsocial__form__content">
                    <div className="body-comsocial__form__content__img">
                        <img
                            src={`/assets/img/avatares/${dataUsuario?.avatar}`}
                            alt="Foto de perfil"
                        />
                    </div>

                    <textarea
                        ref={textareaRef}
                        className="body-comsocial__form__content__text"
                        name="posteo"
                        id="posteo"
                        value={texto}
                        onChange={(e) => setTexto(e.target.value)}
                    />

                    <div className="body-comsocial__form__content__libro">
                        {mostrarBuscadorLibro ? (
                            <div
                                ref={buscadorLibroRef}
                                className="body-comsocial__form__content__libro__buscador"
                                onKeyDown={handleTeclaBuscadorLibro}
                            >
                                <AutoCompletarLibro
                                    placeholder="Buscar libro por título"
                                    value={busquedaLibro}
                                    onChange={(e) => setBusquedaLibro(e.target.value)}
                                    onSelect={handleSeleccionarLibro}
                                    required={false}
                                />
                            </div>
                        ) : (
                            libroSeleccionado && (
                                <div className="body-comsocial__form__content__libro__seleccionado">
                                    <LibroCard
                                        urlFoto={libroSeleccionado.urlFoto}
                                        titulo={libroSeleccionado.titulo}
                                        estrllas={true}
                                    />
                                    <button
                                        type="button"
                                        className="body-comsocial__form__content__libro__seleccionado__quitar"
                                        aria-label="Quitar libro"
                                        onClick={() => setLibroSeleccionado(null)}
                                    >
                                        &times;
                                    </button>
                                </div>
                            )
                        )}
                    </div>
                </div>

                <div className="body-comsocial__form__options">
                    <div className="body-comsocial__form__options__acciones">
                        <div className="body-comsocial__form__options__buttons">
                            <button
                                type="button"
                                className="body-comsocial__form__options__buttons__boton"
                                aria-label="Mencionar usuario"
                                onClick={handleMencionarUsuario}
                            >
                                <MencionarUsuarioPosteo />
                            </button>
                            <button
                                type="button"
                                className="body-comsocial__form__options__buttons__boton"
                                aria-label="Mencionar libro"
                                onClick={handleAlternarBuscadorLibro}
                            >
                                <MencionarLibroPosteo />
                            </button>
                            <SubirImagenPosteo />
                        </div>
                        <div className="body-comsocial__form__options__buttons">
                            <ColorLetra />
                            <NegritaPosteo />
                            <SubrayarPosteo />
                            <TacharPosteo />
                        </div>
                        <div className="body-comsocial__form__options__buttons">
                            <p>Contiene spoilers</p>
                            <label className="body-comsocial__form__options__buttons__label">
                                <input
                                    id="spoiler"
                                    name="spoiler"
                                    className="body-comsocial__form__options__buttons__checkbox"
                                    type="checkbox"
                                    checked={contieneSpoilers}
                                    onChange={(e) => setContieneSpoilers(e.target.checked)}
                                />
                                <span className="body-comsocial__form__options__buttons__checkbox-custom" />
                            </label>
                        </div>
                    </div>
                    <Button
                        type="submit"
                        variant="default"
                        color="oscuro"
                        isDisabled={!texto.trim() || loadingCrear}
                    >
                        Publicar
                    </Button>
                </div>
            </form>

            <section>
                {dataPosteos.content &&
                    dataPosteos.content.map((posteo) => (
                        <ComentarioSocial
                            key={posteo.id}
                            nickname={posteo.usuario?.nickname}
                            urlFoto={`/assets/img/avatares/${posteo.usuario?.avatar}`}
                            tiempoPublicacion={posteo.tiempoTranscurrido}
                        >
                            {posteo.textoPublicacion}
                        </ComentarioSocial>
                    ))}
            </section>
        </main>
    );
};