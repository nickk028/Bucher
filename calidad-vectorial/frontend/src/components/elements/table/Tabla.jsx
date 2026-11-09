import './Tabla.css';

export const Tabla = ({ columnas, items, claveItem = 'id', mensajeVacio = 'No hay datos para mostrar.' }) => {
    return (
        <div className="tabla">
            <table className="tabla__elemento">
                <thead className="tabla__elemento__cabecera">
                    <tr>
                        {columnas.map((columna) => (
                            <th key={columna.clave}>{columna.titulo}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {items.map((item) => (
                        <tr key={item[claveItem]} className="tabla__elemento__fila">
                            {columnas.map((columna) => (
                                <td
                                    key={columna.clave}
                                    className={columna.negrita ? 'tabla__elemento__fila__celda--negrita' : ''}
                                >
                                    {columna.render ? columna.render(item) : item[columna.clave]}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>

            {items.length === 0 && <p className="tabla__vacio">{mensajeVacio}</p>}
        </div>
    );
};