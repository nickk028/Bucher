import { useState } from "react";
import { validarExisteConfig, setConfig } from "../../utils/ConfigUtils";
import { Autocompletar } from "../autocomplete/Autocompletar";
import { Button } from "../buttons/Button";
import "./Configuracion.css";

export const ConfiguracionAplicacion = () => {
    // Carga/ crea la configuración al montar
    const [configuracion, setConfiguracion] = useState(() => validarExisteConfig());
    const coloresBuchy = [ "azul", "rojo", "amarillo", "dorado", "verde-oscuro", "verde-claro" ];
    
    const onBoolChange = (e) => {
        const { name, checked } = e.target;   // siempre boolean
        setConfiguracion(prev => ({ ...prev, [name]: checked }));
    };

    const onStringChange = (nombre) => (valor) => {
        setConfiguracion(prev => ({ ...prev, [nombre]: valor .target.value }));
    }

    const onConfigSubmit = (e) => {
        e.preventDefault();
        if (coloresBuchy.includes(configuracion.colorBuchy)) {
            setConfig(configuracion);
        } else {
            console.log("Color de Buchy inválido");
            // ns como mostrar error
        }
    }

    return (
        <div className="config-content">
            <h1 className="config-content__title">Configuración aplicación</h1>
            <label>
                <form onSubmit={onConfigSubmit}>
                    <input type="checkbox" checked={!!configuracion.buchy} onChange={onBoolChange} name="buchy"/>
                    Activar "buchy"
                    <Autocompletar
                        options = {coloresBuchy}
                        placeholder = "Color Buchy"
                        name = "colorBuchy"
                        value = {configuracion.colorBuchy}
                        onChange = {onStringChange("colorBuchy")}
                        />
                    <Button type="submit" variant="default" color="oscuro" disabled={false}>Guardar Configuración</Button>
                </form>
            </label>

            {/* JSON guardado */}
            <p>{JSON.stringify(configuracion, null, 2)}</p>
        </div>
    );
};