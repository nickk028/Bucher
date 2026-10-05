import { useEffect, useState } from 'react';
import { Input } from '../../../elements/input/Input';
import { Button } from '../../../elements/buttons/Button';
import IconoSubirImagen from '../../../../assets/img/icons/crear/subirImagenLibro.svg?react';
import IconoChevron from '../../../../assets/img/icons/preguntasfrecuentes/flechaDesplegar.svg?react';
import './CrearLibro.css';

const generosDisponibles = [
    'Aventura',
    'Ciencia Ficción',
    'Fantástico',
    'Suspenso',
    'Policial',
    'Realismo Mágico',
    'Romance',
    'Terror',
    'Histórico',
    'Psicología',
    'Drama',
    'Humor',
    'Infantil',
];

export const CrearLibro = () => {
    const [formData, setFormData] = useState({
        titulo: '',
        autor: '',
        editorial: '',
        sinopsis: '',
    });
    const [generos, setGeneros] = useState([]);
    const [portada, setPortada] = useState(null);
    const [portadaUrl, setPortadaUrl] = useState('');

    useEffect(() => {
        if (!portada) {
            setPortadaUrl('');
            return;
        }

        const url = URL.createObjectURL(portada);
        setPortadaUrl(url);

        return () => URL.revokeObjectURL(url);
    }, [portada]);

    const formularioValido = formData.titulo.trim() !== '' && formData.autor.trim() !== '';
    const generosSinElegir = generosDisponibles.filter((genero) => !generos.includes(genero));

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleAgregarGenero = (e) => {
        const genero = e.target.value;
        if (genero && !generos.includes(genero)) {
            setGeneros([...generos, genero]);
        }
    };

    const handleQuitarGenero = (genero) => {
        setGeneros(generos.filter((actual) => actual !== genero));
    };

    const seleccionarPortada = (archivo) => {
        if (archivo && archivo.type.startsWith('image/')) {
            setPortada(archivo);
        }
    };

    const handleDropPortada = (e) => {
        e.preventDefault();
        seleccionarPortada(e.dataTransfer.files[0]);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log({ ...formData, generos, portada });
    };

    return (
        <div className="publicar-content">
            <div>
                <h1 className="publicar-content__title">Publica tu libro</h1>
                <p>Comparte tu producción con nuestra comunidad</p>
            </div>

            <form className="publicar-content__layout" onSubmit={handleSubmit}>
                <div className="publicar-content__form">
                    <h2>Datos del libro</h2>

                    <div className="publicar-content__campo publicar-content__campo--completo">
                        <label className="publicar-content__label" htmlFor="titulo">
                            Título del libro
                        </label>
                        <Input
                            id="titulo"
                            type="text"
                            name="titulo"
                            value={formData.titulo}
                            onChange={handleChange}
                            required={true}
                        />
                    </div>

                    <div className="publicar-content__campo publicar-content__campo--medio">
                        <label className="publicar-content__label" htmlFor="autor">
                            Autor
                        </label>
                        <Input
                            id="autor"
                            type="text"
                            name="autor"
                            value={formData.autor}
                            onChange={handleChange}
                            required={true}
                        />
                    </div>

                    <div className="publicar-content__campo publicar-content__campo--corto">
                        <div className="publicar-content__campo__header">
                            <label className="publicar-content__label" htmlFor="editorial">
                                Editorial
                            </label>
                            <span className="publicar-content__opcional">(Opcional)</span>
                        </div>
                        <Input
                            id="editorial"
                            type="text"
                            name="editorial"
                            value={formData.editorial}
                            onChange={handleChange}
                            required={false}
                        />
                    </div>

                    <div className="publicar-content__campo publicar-content__campo--completo">
                        <div className="publicar-content__campo__header">
                            <span className="publicar-content__label">Portada</span>
                            <span className="publicar-content__opcional">(Opcional)</span>
                        </div>
                        <label
                            className="publicar-content__dropzone"
                            htmlFor="portada"
                            onDragOver={(e) => e.preventDefault()}
                            onDrop={handleDropPortada}
                        >
                            <div className='publicar-content__dropzone__img'>
                                <IconoSubirImagen
                                className="publicar-content__dropzone__img__icono"
                                aria-hidden="true"
                            />
                            </div>
                            <span>{portada ? portada.name : 'Sube la imagen de la portada del libro'}</span>
                            <input
                                id="portada"
                                className="publicar-content__dropzone__input"
                                type="file"
                                accept="image/*"
                                onChange={(e) => seleccionarPortada(e.target.files[0])}
                            />
                        </label>
                    </div>

                    <div className="publicar-content__campo publicar-content__campo--medio">
                        <label className="publicar-content__label" htmlFor="generos">
                            Géneros
                        </label>
                        <div className="publicar-content__select">
                            <select id="generos" value="" onChange={handleAgregarGenero}>
                                <option value=""></option>
                                {generosSinElegir.map((genero) => (
                                    <option key={genero} value={genero}>
                                        {genero}
                                    </option>
                                ))}
                            </select>
                            <IconoChevron
                                className="publicar-content__select__chevron"
                                aria-hidden="true"
                            />
                        </div>
                    </div>

                    <div className="publicar-content__campo publicar-content__campo--completo">
                        <label className="publicar-content__label" htmlFor="sinopsis">
                            Sinopsis
                        </label>
                        <Input
                            id="sinopsis"
                            variant="medio"
                            type="text"
                            name="sinopsis"
                            value={formData.sinopsis}
                            onChange={handleChange}
                            required={false}
                        />
                    </div>
                </div>

                <div className="publicar-content__lateral">
                    <span className="publicar-content__label">Vista previa</span>

                    <article className="publicar-content__preview">
                        <div className="publicar-content__preview__portada">
                            {portadaUrl && <img src={portadaUrl} alt="Portada del libro" />}
                        </div>

                        <div className="publicar-content__preview__info">
                            <h3>{formData.titulo || 'Título del libro'}</h3>
                            <p className="publicar-content__preview__autor">
                                {formData.autor || 'Autor/es'}
                            </p>

                            <div className="publicar-content__preview__generos">
                                <span>Géneros:</span>
                                <ul className="publicar-content__preview__chips">
                                    {generos.map((genero) => (
                                        <li key={genero}>
                                            <button
                                                type="button"
                                                className="publicar-content__chip"
                                                aria-label={`Quitar género ${genero}`}
                                                onClick={() => handleQuitarGenero(genero)}
                                            >
                                                {genero}
                                            </button>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    </article>

                    <div className="publicar-content__accion">
                        <Button
                            type="submit"
                            variant="default"
                            color="oscuro"
                            isDisabled={!formularioValido}
                        >
                            Publicar
                        </Button>
                    </div>
                </div>
            </form>
        </div>
    );
};