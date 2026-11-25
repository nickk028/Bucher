import iconoCheck from '../../../assets/img/icons/crear/tildeVistaPrevia.svg';
import './LibroDetalle.css';

export const LibroDetalle = ({
    urlFoto,
    titulo,
    autor,
    editorial,
    caracteristicas = [],
    detalles = [],
    detallesEjemplar
}) => {
    const tieneReferencia = urlFoto && titulo;

    const mostrarDetallesEjemplar = Array.isArray(detallesEjemplar);

    return (
        <article
            className={
                'libro-detalle' +
                (mostrarDetallesEjemplar ? ' libro-detalle--ejemplar' : '')
            }
        >
            {tieneReferencia ? (
                <img
                    className='libro-detalle__img'
                    src={urlFoto}
                    alt="Foto del libro"
                />
            ) : (
                <div className='libro-detalle__img libro-detalle__img--vacia' />
            )}

            <div className='libro-detalle__text'>
                {tieneReferencia ? (
                    <>
                        <h3>{titulo}</h3>
                        <p>{autor}</p>
                    </>
                ) : (
                    <p className='libro-detalle__sin-referencia'>
                        No hay referencia a un libro
                    </p>
                )}

                {caracteristicas.length !== 0 && (
                    <ul className='libro-detalle__caracteristicas'>
                        {caracteristicas.map((caracteristica) => (
                            <li
                                key={caracteristica}
                                className='libro-detalle__caracteristicas__item'
                            >
                                <img
                                    className="libro-detalle__caracteristicas__icono"
                                    src={iconoCheck}
                                    alt=""
                                />
                                <span>{caracteristica}</span>
                            </li>
                        ))}
                    </ul>
                )}

                {mostrarDetallesEjemplar && (
                    <div className='libro-detalle__ejemplar'>
                        <span className='libro-detalle__ejemplar__label'>
                            Detalles del ejemplar:
                        </span>

                        {detallesEjemplar.length !== 0 ? (
                            <ul className='libro-detalle__ejemplar__chips'>
                                {detallesEjemplar.map((detalle) => (
                                    <li
                                        key={detalle}
                                        className='libro-detalle__ejemplar__chip'
                                    >
                                        {detalle}
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p className='libro-detalle__sin-referencia'>
                                Sin detalles adicionales
                            </p>
                        )}
                    </div>
                )}
            </div>

            <ul className='libro-detalle__aside'>
                {detalles.map(({ label, texto }) => (
                    <li key={label} className='libro-detalle__aside__item'>
                        <span className='libro-detalle__aside__item__label'>
                            {label}
                        </span>
                        <p className='libro-detalle__aside__item__texto'>
                            {texto}
                        </p>
                    </li>
                ))}
            </ul>
        </article>
    );
};