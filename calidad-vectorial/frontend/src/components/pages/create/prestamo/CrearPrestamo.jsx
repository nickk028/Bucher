import React, { useState } from 'react';
import { usePost } from '../../../utils/FetchUtils';
import './CrearPrestamo.css';
import { Button } from '../../../elements/buttons/Button';
import { AutoCompletarLibro } from '../../../elements/autocomplete/types/AutoCompletarLibro';
import { LibroDetalle } from '../../../elements/book/LibroDetalle';

const CONDITIONS = ['Excelente', 'Muy bueno', 'Bueno', 'Desgastado'];

const ADDITIONAL_DETAILS = [
    { id: 'subrayado', label: 'Ejemplar subrayado.', valor: 'subrayado', chip: 'Subrayado' },
    { id: 'anotaciones', label: 'Ejemplar con anotaciones.', valor: 'con anotaciones', chip: 'Con anotaciones' },
    { id: 'manchas', label: 'Ejemplar con manchas.', valor: 'con manchas', chip: 'Con manchas' },
    { id: 'paginas_dobladas', label: 'Ejemplar con páginas dobladas.', valor: 'con páginas dobladas', chip: 'Con páginas dobladas' },
];

const LOAN_DURATIONS = [
    { value: 1, label: '1 semana' },
    { value: 2, label: '2 semanas' },
    { value: 3, label: '3 semanas' },
    { value: 4, label: '4 semanas' },
    { value: 5, label: '5 semanas' },
    { value: 6, label: '6 semanas' },
    { value: 7, label: '7 semanas' },
    { value: 8, label: '8 semanas' },
];

export const CrearPrestamo = () => {
    const [bookSearch, setBookSearch] = useState('');
    const [selectedBook, setSelectedBook] = useState(null);
    const [condition, setCondition] = useState('Muy bueno');
    const [details, setDetails] = useState({ subrayado: true });
    const [comments, setComments] = useState('');
    const [photos, setPhotos] = useState([]);
    const [loanDuration, setLoanDuration] = useState(1);

    const { data: dataPrestamo, loading: loadingPrestamo, error: errorPrestamo, execute: executePrestamo } = usePost('publicacion/crear')

    // Acepta tanto un evento como un valor directo
    const handleBookChange = (e) => {
        setBookSearch(e?.target ? e.target.value : (e ?? ''));
        setSelectedBook(null);
    };

    const handleBookSelect = (book) => {
        setSelectedBook(book);
        setBookSearch(book?.titulo ?? book?.title ?? '');
    };

    const toggleDetail = (id) => {
        setDetails((prev) => ({ ...prev, [id]: !prev[id] }));
    };

    const handlePhotoUpload = (e) => {
        const files = Array.from(e.target.files);
        const previews = files.map((f) => URL.createObjectURL(f));
        setPhotos((prev) => [...prev, ...previews]);
    };

    function handleLoanSubmit(e) {
        e?.preventDefault?.();

        executePrestamo({
            titulo: selectedBook?.titulo ?? selectedBook?.title ?? bookSearch.trim(),
            descripcion: comments,
            limiteDias: loanDuration * 7,
            detallesEstadoLibro: condition,
            detallesAdicionales: selectedDetails.map((d) => d.valor),
        });
    }

    const selectedDetails = ADDITIONAL_DETAILS.filter((d) => details[d.id]);

    const sliderPercent = ((loanDuration - 1) / (LOAN_DURATIONS.length - 1)) * 100;

    const currentDurationLabel =
        LOAN_DURATIONS.find((d) => d.value === loanDuration)?.label ?? '1 semana';

    const caracteristicas = [
        `Estado del ejemplar: ${condition}`,
        `Duración del préstamo: ${currentDurationLabel}`,
    ];

    const detallesEjemplar = selectedDetails.map((detalle) => detalle.chip);

    return (
        <form className="clp-page">
            <div className="clp-form-col">
                <div className="clp-header">
                    <h1 className="clp-title">Crea una publicación de préstamo</h1>
                    <p className="clp-subtitle">Comparte tu libro con nuestra comunidad</p>
                </div>

                {/* Seleccionar libro */}
                <section className="clp-card">
                    <h2 className="clp-card-title">Seleccionar libro</h2>
                    <AutoCompletarLibro
                        placeholder="Busca por título, autor o editorial..."
                        value={bookSearch}
                        onChange={handleBookChange}
                        onSelect={handleBookSelect}
                    />
                </section>

                {/* Estado del ejemplar */}
                <section className="clp-card">
                    <h2 className="clp-card-title">Estado del ejemplar</h2>

                    <p className="clp-label">Estado general</p>
                    <div className="clp-condition-group">
                        {CONDITIONS.map((c) => (
                            <button
                                key={c}
                                type="button"
                                className={`clp-condition-btn ${condition === c ? 'clp-condition-btn--active' : ''}`}
                                onClick={() => setCondition(c)}
                            >
                                {c}
                            </button>
                        ))}
                    </div>

                    <p className="clp-label">Detalles adicionales</p>
                    <div className="clp-details-group">
                        {ADDITIONAL_DETAILS.map((d) => (
                            <label key={d.id} className="clp-checkbox-label">
                                <input
                                    type="checkbox"
                                    className="clp-checkbox"
                                    checked={!!details[d.id]}
                                    onChange={() => toggleDetail(d.id)}
                                />
                                <span className="clp-checkbox-custom" />
                                {d.label}
                            </label>
                        ))}
                    </div>

                    <p className="clp-label">Comentarios adicionales</p>
                    <textarea
                        className="clp-textarea"
                        placeholder="Describe cualquier detalle relevante sobre tu ejemplar..."
                        value={comments}
                        onChange={(e) => setComments(e.target.value)}
                        rows={4}
                    />
                </section>

                {/* Fotos del libro */}
                {/* <section className="clp-card">
                    <h2 className="clp-card-title">Fotos del libro</h2>
                    <label className="clp-upload-area">
                        <input
                            type="file"
                            accept="image/*"
                            multiple
                            className="clp-upload-input"
                            onChange={handlePhotoUpload}
                        />

                        {photos.length === 0 ? (
                            <div className="clp-upload-placeholder">
                                <div className="clp-upload-placeholder-img">
                                    <SubirImagenLibro />
                                </div>
                                <span>Sube tus propias imágenes del ejemplar</span>
                            </div>
                        ) : (
                            <div className="clp-photo-previews">
                                {photos.map((src, i) => (
                                    <img
                                        key={i}
                                        src={src}
                                        alt={`foto ${i + 1}`}
                                        className="clp-photo-thumb"
                                    />
                                ))}
                                <div className="clp-upload-add">＋</div>
                            </div>
                        )}
                    </label>
                </section> */}

                {/* Duración del préstamo */}
                <section className="clp-card">
                    <h2 className="clp-card-title">Duración del préstamo</h2>
                    <div className="clp-slider-wrapper">
                        <input
                            type="range"
                            min={1}
                            max={8}
                            step={1}
                            value={loanDuration}
                            onChange={(e) => setLoanDuration(Number(e.target.value))}
                            className="clp-slider"
                            style={{ '--fill': `${sliderPercent}%` }}
                        />
                        <span className="clp-slider-label">{currentDurationLabel}</span>
                    </div>
                </section>
            </div>

            {/* Vista previa */}
            <aside className="clp-preview-col">
                <p className="clp-preview-heading">Vista previa</p>

                <LibroDetalle
                    urlFoto={selectedBook?.urlFoto}
                    titulo={selectedBook?.titulo ?? selectedBook?.title ?? bookSearch.trim()}
                    autor={selectedBook?.autor}
                    caracteristicas={caracteristicas}
                    detallesEjemplar={detallesEjemplar}
                />

                <div className="clp-publish-btn">
                    <Button variant="default" color="oscuro" onClick={handleLoanSubmit}>
                        Publicar
                    </Button>
                </div>
            </aside>
        </form>
    );
};