import { useEffect, useState } from 'react';
import { validarExisteConfig, setConfig } from '../../../utils/ConfigUtils';
import { Usuario } from '../../../elements/user/Usuario';

export const Configuracion = () => {
    // Carga/ crea la configuración al montar
    const [configuracion, setConfiguracion] = useState(() => validarExisteConfig());

    // Actualiza cada vez que cambia configuracion
    useEffect(() => {
        setConfig(configuracion);
    }, [configuracion]);
    
    const onConfigChange = (e) => {
        const { name, checked } = e.target;   // siempre boolean
        setConfiguracion(prev => ({ ...prev, [name]: checked }));
    };

    return (
        <div>
            <label>
                {/* (!!) Transforma cualquier valor a boolean */}
                <input type="checkbox" checked={!!configuracion.buchy} onChange={onConfigChange} name="buchy"/>
                Activar "buchy"
            </label>

            {/* JSON guardado */}
            <p>{JSON.stringify(configuracion, null, 2)}</p>
        </div>
    );
};