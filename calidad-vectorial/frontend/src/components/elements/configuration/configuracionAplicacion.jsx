import { useState } from "react";
import { validarExisteConfig, setConfig } from "../../utils/ConfigUtils";
import { Autocompletar } from "../autocomplete/Autocompletar";
import { Button } from "../buttons/Button";
import { Input } from "../input/Input";
import "./Configuracion.css";
import buchyAzul from "../../../assets/img/buchy/buchyAzul.png";
import buchyRojo from "../../../assets/img/buchy/buchyRojo.png";
import buchyAmarillo from "../../../assets/img/buchy/buchyAmarillo.png";
import buchyDorado from "../../../assets/img/buchy/buchyDorado.png";
import buchyVerdeOscuro from "../../../assets/img/buchy/buchyVerdeOscuro.png";
import buchyVerdeClaro from "../../../assets/img/buchy/buchyVerdeClaro.png";

export const ConfiguracionAplicacion = () => {
    // Carga/ crea la configuración al montar
    const [configuracion, setConfiguracion] = useState(() => validarExisteConfig());
    const coloresBuchy = [
        [buchyAzul ,"azul"], [buchyRojo, "rojo"], [buchyAmarillo,"amarillo"],
        [buchyDorado,"dorado"], [buchyVerdeOscuro,"verde-oscuro"], [buchyVerdeClaro,"verde-claro"] ];

    const onBoolChange = (e) => {
        console.log(e.target);
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
                <Input type="checkbox" checked={!!configuracion.buchy} onChange={onBoolChange} name="buchy"> Activar Büchy</Input>
                <div className="config-content__input-group">
                    <label className="config-content__form__label" htmlFor="colorBuchy">Color de Büchy</label>
                    <Autocompletar
                        options = {coloresBuchy}
                        placeholder = "Color Buchy"
                        name = "colorBuchy"
                        tipo = "doble"
                        imgHeight = "64px"
                        imgWidth = "60px"
                        value = {configuracion.colorBuchy}
                        onChange = {onStringChange("colorBuchy")}
                    />
                </div>
                <div>
                    <Button type="submit" variant="default" color="oscuro" disabled={false}>Guardar Configuración</Button>
                </div>
            </form>
            {configuracion && ( JSON.stringify(configuracion) )}
        </div>
    );
};