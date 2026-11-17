import iconoCheck from '../../../assets/img/icons/crear/tildeVistaPrevia.svg';
import './LibroDetalle.css';

export const LibroDetalle = ({ urlFoto, titulo, autor, editorial, caracteristicas=[], detalles=[] }) => {
    return (
        <article className='libro-detalle'>
            <img className='libro-detalle__img' src={urlFoto} alt="Foto del libro" />
            <div className='libro-detalle__text'>
                <h3>{titulo}</h3>
                <p>{autor}</p>
                {caracteristicas.length !== 0 && (
                    <ul className='libro-detalle__caracteristicas'>
                        {caracteristicas.map((caracteristica) => (
                            <li key={caracteristica} className='libro-detalle__caracteristicas__item'>
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
            </div>
            <ul className='libro-detalle__aside'>
                {detalles.map(({label, texto}) => (
                    <li key={label} className='libro-detalle__aside__item'>
                        <span className='libro-detalle__aside__item__label'>{label}</span>
                        <p className='libro-detalle__aside__item__texto'>{texto}</p>
                    </li>
                ))}
            </ul>
        </article>
    );
};