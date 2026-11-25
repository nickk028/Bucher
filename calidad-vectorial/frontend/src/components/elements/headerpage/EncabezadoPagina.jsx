import './EncabezadoPagina.css'

export const EncabezadoPagina = ({titulo='', subtitulo=''}) => {
    return (
        <header className='encabezado-pagina'>
            <h1 className='encabezado-pagina__titulo'>{titulo}</h1>
            <p className='encabezado-pagina__subtitulo'>{subtitulo}</p>
        </header>
    );
};