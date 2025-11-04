import { useState } from "react";
import { validarExisteConfig, setConfig } from "../../utils/ConfigUtils";
import { Autocompletar } from "../autocomplete/Autocompletar";
import { Button } from "../buttons/Button";
import { Input } from "../input/Input";
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
            <form className="config-content__form" onSubmit={onConfigSubmit}>
                <Input type="checkbox" checked={!!configuracion.buchy} onChange={onBoolChange} name="buchy"> Activar Buchy</Input>
                <div className="config-content__input-group">
                    <label className="config-content__form__label" htmlFor="colorBuchy">Color de Buchy</label>
                    <Autocompletar
                    options = {coloresBuchy}
                    placeholder = "Color Buchy"
                    name = "colorBuchy"
                    value = {configuracion.colorBuchy}
                    onChange = {onStringChange("colorBuchy")}
                    />
                </div>
                <div>
                    <Button type="submit" variant="default" color="oscuro" disabled={false}>Guardar Configuración</Button>
                </div>
            </form>
        </div>
    );
};