import { useEffect, useState, useRef } from 'react';
import { Input } from '../../../elements/input/Input';
import { Button } from '../../../elements/buttons/Button';
import { useFetch, usePost } from '../../../utils/FetchUtils';
import flecha from '../../../../assets/img/icons/utils/flechaGris.png';
import alicia from '../../../../../public/assets/img/avatares/alicia.png';
import bruja from '../../../../../public/assets/img/avatares/bruja.png';
import caperucitaRoja from '../../../../../public/assets/img/avatares/caperucitaRoja.png';
import cenicienta from '../../../../../public/assets/img/avatares/cenicienta.png';
import detective from '../../../../../public/assets/img/avatares/detective.png';
import dorothy from '../../../../../public/assets/img/avatares/dorothy.png';
import dracula from '../../../../../public/assets/img/avatares/dracula.png';
import frankenstein from '../../../../../public/assets/img/avatares/frankenstein.png';
import gandalf from '../../../../../public/assets/img/avatares/gandalf.png';
import harryPotter from '../../../../../public/assets/img/avatares/harryPotter.png';
import hombreDeHojalata from '../../../../../public/assets/img/avatares/hombreDeHojalata.png';
import katniss from '../../../../../public/assets/img/avatares/katniss.png';
import pinocho from '../../../../../public/assets/img/avatares/pinocho.png';
import rapunzel from '../../../../../public/assets/img/avatares/rapunzel.png';
import reinaDeCorazones from '../../../../../public/assets/img/avatares/reinaDeCorazones.png';

import './EditarPerfil.css';

const AVATARES = [
    { id: 'ALICIA', src: alicia },
    { id: 'BRUJA', src: bruja },
    { id: 'CAPERUCITA_ROJA', src: caperucitaRoja },
    { id: 'CENICIENTA', src: cenicienta },
    { id: 'DETECTIVE', src: detective },
    { id: 'DOROTHY', src: dorothy },
    { id: 'DRACULA', src: dracula },
    { id: 'FRANKENSTEIN', src: frankenstein },
    { id: 'GANDALF', src: gandalf },
    { id: 'HARRY_POTTER', src: harryPotter },
    { id: 'HOMBRE_DE_HOJALATA', src: hombreDeHojalata },
    { id: 'KATNISS', src: katniss },
    { id: 'PINOCHO', src: pinocho },
    { id: 'RAPUNZEL', src: rapunzel },
    { id: 'REINA_DE_CORAZONES', src: reinaDeCorazones },
];

const normalizarAvatar = (avatar) =>
    avatar
        ? String(avatar)
            .split('/')
            .pop()
            .replace(/\.png$/i, '')
            .toUpperCase()
        : '';

const vacioANull = (valor) => (valor === '' || valor === undefined ? null : valor);


export const EditarPerfil = () => {
    const {
        data: respuestaUsuario,
        loading: loadingUsuario,
        error: errorUsuario,
    } = useFetch('usuario/propio');
    const {
        data: respuestaDataUsuario,
        error: errorDataUsuario,
        loading: loadingDataUsuario,
        execute: executeDataUsuario,
    } = usePost('usuario/modificar', 'PUT');

    const [editando, setEditando] = useState(false);
    const [formData, setFormData] = useState({
        avatar: '',
        username: '',
        pronombres: '',
        descripcion: '',
        nickname: '',
        direccion: '',
        piso: '',
        codigoPostal: '',
    });

    const carruselRef = useRef(null);

    const desplazarCarrusel = (direccion) => {
        carruselRef.current?.scrollBy({
            left: direccion * 200,
            behavior: 'smooth',
        });
    };

    // Carga los datos propios del usuario como valores por defecto
    useEffect(() => {
        if (respuestaUsuario) {
            setFormData({
                avatar: normalizarAvatar(respuestaUsuario.avatar),
                username: respuestaUsuario.username ?? '',
                pronombres: respuestaUsuario.pronombres ?? '',
                descripcion: respuestaUsuario.descripcion ?? '',
                nickname: respuestaUsuario.nickname ?? '',
                direccion: respuestaUsuario.direccion ?? '',
                piso: respuestaUsuario.piso ?? '',
                codigoPostal: respuestaUsuario.codigoPostal ?? '',
            });
        }
    }, [respuestaUsuario]);

    const handleSubmitDataUser = async (e) => {
        e.preventDefault();
        if (!editando) return;

        // Los campos vacíos se envían como null
        const payload = {
            avatar: vacioANull(formData.avatar),
            username: vacioANull(formData.username),
            nickname: vacioANull(formData.nickname),
            pronombres: vacioANull(formData.pronombres),
            descripcion: vacioANull(formData.descripcion),
            direccion: vacioANull(formData.direccion),
            piso: vacioANull(formData.piso),
            codigoPostal: vacioANull(formData.codigoPostal),
        };

        try {
            await executeDataUsuario(payload);
            setEditando(false);
        } catch (err) {
            console.error('No se pudo modificar el perfil', err);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSeleccionarAvatar = (id) => {
        if (!editando) return;
        setFormData((prev) => ({ ...prev, avatar: id }));
    };

    const labelClass = `config-content__form__label config-content__form__label--${editando ? 'editando' : ''}`;

    return (
        <form className="config-content" onSubmit={handleSubmitDataUser}>
            <div>
                <h1 className="config-content__title">Editar perfil</h1>
                <p>Modifica tu usuario a tu gusto</p>
            </div>
            <div className="config-content__form">
                <h2>Tu usuario</h2>
                <div>
                    <label className={labelClass}>Correo electrónico</label>
                    <Input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        disabled={!editando}
                        required={false}
                    />
                </div>

                <div className="config-content__form__apodo">
                    <label className={labelClass}>Apodo</label>
                    <Input
                        type="text"
                        name="nickname"
                        value={formData.nickname}
                        onChange={handleChange}
                        disabled={!editando}
                        required={false}
                    />
                </div>

                <div className="config-content__form__pronombres">
                    <label className={labelClass}>Pronombres</label>
                    <Input
                        type="text"
                        name="pronombres"
                        value={formData.pronombres}
                        onChange={handleChange}
                        disabled={!editando}
                        required={false}
                    />
                </div>

                <div>
                    <label className={labelClass}>Descripción</label>
                    <Input
                        variant="medio"
                        type="text"
                        name="descripcion"
                        value={formData.descripcion}
                        onChange={handleChange}
                        disabled={!editando}
                        required={false}
                    />
                </div>

                <div className="carrusel-contenedor">
                    <button
                        type="button"
                        className="carrusel__flecha"
                        onClick={() => desplazarCarrusel(-1)}
                        aria-label="Avatares anteriores"
                    >
                        <img src={flecha} alt="" />
                    </button>

                    <div className="carrusel" ref={carruselRef}>
                        {AVATARES.map((a) => (
                            <img
                                key={a.id}
                                src={a.src}
                                alt={a.id.toLowerCase()}
                                onClick={() => handleSeleccionarAvatar(a.id)}
                                style={{
                                    cursor: editando ? 'pointer' : 'default',
                                    opacity: editando && formData.avatar !== a.id ? 0.6 : 1,
                                    outline:
                                        formData.avatar === a.id
                                            ? '3px solid var(--brand-primary, currentColor)'
                                            : 'none',
                                    borderRadius: '50%',
                                }}
                            />
                        ))}
                    </div>

                    <button
                        type="button"
                        className="carrusel__flecha carrusel__flecha--derecha"
                        onClick={() => desplazarCarrusel(1)}
                        aria-label="Avatares siguientes"
                    >
                        <img src={flecha} alt="" />
                    </button>
                </div>
            </div>

            <div className="config-content__form">
                <h2>Tu dirección</h2>
                <div>
                    <label className={labelClass}>Calle</label>
                    <Input
                        type="text"
                        name="direccion"
                        value={formData.direccion}
                        onChange={handleChange}
                        disabled={!editando}
                        required={false}
                    />
                </div>

                <div className="contenedor">
                    <div>
                        <label className={labelClass}>Piso</label>
                        <Input
                            type="text"
                            name="piso"
                            value={formData.piso}
                            onChange={handleChange}
                            disabled={!editando}
                            required={false}
                        />
                    </div>

                    <div>
                        <label className={labelClass}>Código postal</label>
                        <Input
                            type="text"
                            name="codigoPostal"
                            value={formData.codigoPostal}
                            onChange={handleChange}
                            disabled={!editando}
                            required={false}
                        />
                    </div>
                </div>
            </div>
            <div>
                {editando && (
                    <Button key="guardar" type="submit" variant="default" color="oscuro">
                        Guardar cambios
                    </Button>
                )}
                {!editando && (
                    <Button
                        key="editar"
                        type="button"
                        variant="default"
                        color="oscuro"
                        onClick={() => setEditando(true)}
                    >
                        Editar mis datos
                    </Button>
                )}
            </div>
        </form>
    );
};