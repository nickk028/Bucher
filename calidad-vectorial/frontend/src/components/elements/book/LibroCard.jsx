import "./LibroCard.css";
export const LibroCard = ({urlFoto, titulo}) => {
    return (
        <img className="portada-libro" src={urlFoto} alt={`Portada del libro ${titulo}`}/>
    )
};