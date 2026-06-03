import { Autocompletar } from "../AutoCompletar";

export const AutoCompletarLibro = ({ placeholder, onChange, value , ...props}) => {

    return (
        <div>
            <Autocompletar
                urlFetch = "libro/todos"
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