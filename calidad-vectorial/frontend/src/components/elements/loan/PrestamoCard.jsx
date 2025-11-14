import "./PrestamoCard.css";

const PrestamoCard = ({ prestamo }) => {

    const {
        fechaPrestamo,
        fechaDevolucion,
        publicacion
    } = prestamo;

    const {
        titulo,
        urlFoto,
        usuarioCreador,
        nombre,  // autor
        limiteDias
    } = publicacion;

    const calcularDiasRestantes = () => {
        const inicio = new Date(fechaPrestamo);
        const hoy = new Date();

        const diffTiempo = hoy - inicio; 
        const diffDias = Math.floor(diffTiempo / (1000 * 60 * 60 * 24));
        return limiteDias - diffDias;
    };

    const diasRestantes = calcularDiasRestantes();

    const fechaEstimadaDevolucion = () => {
        if (fechaDevolucion) return fechaDevolucion;

        const fecha = new Date(fechaPrestamo);
        fecha.setDate(fecha.getDate() + limiteDias);

        return fecha.toISOString().split("T")[0];
    };

    return (
       <article className="prestamo-card">
        <img className="prestamo-card__image" src={urlFoto} alt="Foto del libro" />

        <ul className="prestamo-card__list">
            <li className="prestamo-card__list__item">
                <h1 className="prestamo-card__list__item__title">{titulo}</h1>
            </li>
            <li>
                <span className="prestamo-card__list__item__subtitle">Autor: </span> 
                {nombre}
            </li>
            <li>
                <span className="prestamo-card__list__item__subtitle">Publicado por: </span>
                {usuarioCreador}
            </li>
            <li>
                <span className="prestamo-card__list__item__subtitle">Fecha préstamo: </span>
                {fechaPrestamo}
            </li>
            <li>
                <span className="prestamo-card__list__item__subtitle">Días restantes: </span>
                {diasRestantes}
            </li>
            <li>
                <span className="prestamo-card__list__item__subtitle">Devolución estimada: </span>
                {fechaEstimadaDevolucion()}
            </li>
        </ul>
    </article>

    );
};

export default PrestamoCard;
