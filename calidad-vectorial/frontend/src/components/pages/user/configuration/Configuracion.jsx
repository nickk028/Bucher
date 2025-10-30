import { ConfiguracionAplicacion } from "../../../elements/configuration/configuracionAplicacion";
import { ConfiguracionDataUsuario } from "../../../elements/configuration/configuracionDataUsuario";

export const Configuracion = () => {
    return (
        <main>
            <ConfiguracionAplicacion />

            <ConfiguracionDataUsuario />
        </main>
    );
};