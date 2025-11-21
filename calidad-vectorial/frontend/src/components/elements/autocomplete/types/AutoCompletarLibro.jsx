import { useFetch } from "../../../utils/FetchUtils";
import { Autocompletar } from "../Autocompletar";

export const AutoCompletarLibro = ({ placeholder, onChange, value , ...props}) => {
    const { data : dataLibros , errorLibros , loadingLibros  } = useFetch("libro/todos");

    return (
        <div>
            <Autocompletar
                options={dataLibros ? dataLibros.map(libro => [libro.urlFoto ,libro.titulo]) : []}
                type = "text"
                tipo = "doble"
                placeholder = {placeholder}
                imgHeight = "100px"
                imagWidth = "60px"
                value = {value}
                name = "titulo"
                onChange = {onChange}
                {...props}>
            </Autocompletar>
        </div>
    )
}