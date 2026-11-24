import { useEffect, useState } from 'react';
import { usePost } from '../../../utils/FetchUtils';
import { Input } from '../../../elements/input/Input';
import { Button } from '../../../elements/buttons/Button';
import { PopUp } from '../../../elements/modal/PopUp';
import IconoSubirImagen from '../../../../assets/img/icons/crear/subirImagenLibro.svg?react';
import IconoChevron from '../../../../assets/img/icons/preguntasfrecuentes/flechaDesplegar.svg?react';
import './CrearLibro.css';

const generosDisponibles = [
    { valor: 'cienciaficcion', nombre: 'Ciencia Ficción' },
    { valor: 'fantastico', nombre: 'Fantástico' },
    { valor: 'suspenso', nombre: 'Suspenso' },
    { valor: 'policial', nombre: 'Policial' },
    { valor: 'realismomagico', nombre: 'Realismo Mágico' },
    { valor: 'romance', nombre: 'Romance' },
    { valor: 'terror', nombre: 'Terror' },
    { valor: 'historico', nombre: 'Histórico' },
    { valor: 'psicologia', nombre: 'Psicología' },
    { valor: 'drama', nombre: 'Drama' },
    { valor: 'humor', nombre: 'Humor' },
    { valor: 'infantil', nombre: 'Infantil' },
];

export const CrearLibro = () => {
    const {data: dataCrear, loading: loadingCrear, error: errorCrear, execute: executeCrear} = usePost('libro/crear');

    const [formData, setFormData] = useState({
        titulo: '',
        paginas: '',
        descripcion: '',
        categorias: [],
        edicion: 'Primera edición',
    });

    const [autor, setAutor] = useState('');
    const [editorial, setEditorial] = useState('');

    const [portada, setPortada] = useState(null);
    const [portadaUrl, setPortadaUrl] = useState('');

    const [mensaje, setMensaje] = useState('');

    const [mostrarPopUp, setMostrarPopUp] = useState(false);

    useEffect(() => {
        if (!portada) {
            setPortadaUrl('');
            return;
        }

        const url = URL.createObjectURL(portada);
        setPortadaUrl(url);

        return () => URL.revokeObjectURL(url);
    }, [portada]);

    useEffect(() => {
        if (dataCrear && !errorCrear) {
            setMostrarPopUp(true);
        }
    }, [dataCrear, errorCrear]);

    const handleDropPortada = (e) => {
        e.preventDefault();
        seleccionarPortada(e.dataTransfer.files[0]);
    };

    const formularioValido =
        formData.titulo.trim() !== '' &&
        formData.paginas !== '' &&
        Number(formData.paginas) > 0;

    const generosSinElegir = generosDisponibles.filter(
        (genero) => !formData.categorias.includes(genero.valor)
    );

    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData(prev => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleAgregarGenero = (e) => {
        const genero = e.target.value;

        if (genero && !formData.categorias.includes(genero)) {
            setFormData(prev => ({
                ...prev,
                categorias: [...prev.categorias, genero],
            }));
        }
    };

    const handleQuitarGenero = (genero) => {
        setFormData(prev => ({
            ...prev,
            categorias: prev.categorias.filter(
                actual => actual !== genero
            ),
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formularioValido || loadingCrear) return;

        setMensaje('');

        const datos = {
            ...formData,
            paginas: Number(formData.paginas),
        };

        try {
            await executeCrear(datos);
            setMensaje('¡Libro publicado correctamente!');
        } catch (error) {
            setMensaje('No se pudo publicar el libro. Intentá nuevamente.');
        }
    };

    return (
        <>
            {mostrarPopUp && (
                    <PopUp
                        onClick={() => {
                            setMostrarPopUp(false);
                        }}
                        titulo="✔️ Libro creado con éxito ✔️"
                    >
                        Tu libro ya ha sido creado con éxito. Ya puedes utilizarlo en futuros préstamos.
                    </PopUp>
                )}
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
                                value={autor}
                                onChange={(e) => setAutor(e.target.value)}
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
                                value={editorial}
                                onChange={(e) => setEditorial(e.target.value)}
                            />
                        </div>

                        <div className="publicar-content__campo publicar-content__campo--corto">
                            <label className="publicar-content__label" htmlFor="paginas">
                                Cantidad de páginas
                            </label>

                            <Input
                                id="paginas"
                                type="number"
                                name="paginas"
                                value={formData.paginas}
                                onChange={handleChange}
                                required={true}
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
                                <div className="publicar-content__select">
                                    <select id="generos" value="" onChange={handleAgregarGenero}>
                                        <option value=""></option>
                                        {generosSinElegir.map((genero) => (
                                            <option key={genero.valor} value={genero.valor}>
                                                {genero.nombre}
                                            </option>
                                        ))}
                                    </select>

                                    <IconoChevron
                                        className="publicar-content__select__chevron"
                                        aria-hidden="true"
                                    />
                                </div>
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
                                name="descripcion"
                                value={formData.descripcion}
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
                                        {formData.categorias.map((genero) => {
                                            const generoEncontrado = generosDisponibles.find(
                                                (g) => g.valor === genero
                                            );

                                            return (
                                                <li key={genero}>
                                                    <button
                                                        type="button"
                                                        className="publicar-content__chip"
                                                        aria-label={`Quitar género ${generoEncontrado.nombre}`}
                                                        onClick={() => handleQuitarGenero(genero)}
                                                    >
                                                        {generoEncontrado.nombre}
                                                    </button>
                                                </li>
                                            );
                                        })}
                                    </ul>
                                </div>
                            </div>
                        </article>

                        <div className="publicar-content__accion">
                            <Button
                                type="submit"
                                variant="default"
                                color="oscuro"
                                isDisabled={!formularioValido || loadingCrear}
                            >
                                {loadingCrear ? 'Publicando...' : 'Publicar'}
                            </Button>
                        </div>
                    </div>
                </form>
            </div>
        </>
    );
};