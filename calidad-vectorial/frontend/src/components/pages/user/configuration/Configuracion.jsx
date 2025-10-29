import { ConfiguracionAplicacion } from "../../../elements/configuration/configuracionAplicacion";
import { ConfiguracionDataUsuario } from "../../../elements/configuration/configuracionDataUsuario";

export const Configuracion = () => {
    return (
        <div>
            <div className="">
                <ConfiguracionAplicacion />
            </div>

            <div className="">
                <ConfiguracionDataUsuario />
            </div>
        </div>
    );
};