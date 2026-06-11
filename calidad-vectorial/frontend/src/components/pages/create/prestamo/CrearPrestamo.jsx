import React, { useState } from "react";
import "./CrearPrestamo.css";
import { Button } from "../../../elements/buttons/Button";
import SubirImagenPosteo from "../../../../assets/img/icons/crear/subirImagenPosteo.svg?react"
import TildeVistaPrevia from "../../../../assets/img/icons/crear/tildeVistaPrevia.svg?react"
import RelojVistaPrevia from "../../../../assets/img/icons/crear/relojVistaPrevia.svg?react"

const CONDITIONS = ["Excelente", "Muy bueno", "Bueno", "Desgastado"];

const ADDITIONAL_DETAILS = [
	{ id: "subrayado", label: "Ejemplar subrayado." },
	{ id: "anotaciones", label: "Ejemplar con anotaciones." },
	{ id: "manchas", label: "Ejemplar con manchas." },
	{ id: "paginas_dobladas", label: "Ejemplar con páginas dobladas." },
];

const LOAN_DURATIONS = [
	{ value: 1, label: "1 semana" },
	{ value: 2, label: "2 semanas" },
	{ value: 3, label: "3 semanas" },
	{ value: 4, label: "1 mes" },
];

export const CrearPrestamo = () => {
	const [bookSearch, setBookSearch] = useState("");
	const [condition, setCondition] = useState("Muy bueno");
	const [details, setDetails] = useState({ subrayado: true });
	const [comments, setComments] = useState("");
	const [photos, setPhotos] = useState([]);
	const [loanDuration, setLoanDuration] = useState(1);

	const toggleDetail = (id) => {
		setDetails((prev) => ({ ...prev, [id]: !prev[id] }));
	};

	const handlePhotoUpload = (e) => {
		const files = Array.from(e.target.files);
		const previews = files.map((f) => URL.createObjectURL(f));
		setPhotos((prev) => [...prev, ...previews]);
	};

	const selectedDetails = ADDITIONAL_DETAILS.filter((d) => details[d.id]);

	const sliderPercent = ((loanDuration - 1) / (LOAN_DURATIONS.length - 1)) * 100;

	const currentDurationLabel = LOAN_DURATIONS.find((d) => d.value === loanDuration)?.label ?? "1 semana";

  	return (
    	<div className="clp-page">
      		<div className="clp-form-col">
        		<div className="clp-header">
          			<h1 className="clp-title">Crea una publicación de préstamo</h1>
					<p className="clp-subtitle">Comparte tu libro con nuestra comunidad</p>
        		</div>

				{/* Seleccionar libro */}
				<section className="clp-card">
					<h2 className="clp-card-title">Seleccionar libro</h2>
					<div className="clp-search-wrapper">
						<span className="clp-search-icon">
							<svg width="16" height="16" viewBox="0 0 20 20" fill="none">
								<circle cx="8.5" cy="8.5" r="5.5" stroke="currentColor" strokeWidth="1.8" />
								<path d="M13.5 13.5L17 17" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" />
							</svg>
						</span>
						<input
						className="clp-search-input"
						type="text"
						placeholder="Busca por título, autor o editorial..."
						value={bookSearch}
						onChange={(e) => setBookSearch(e.target.value)}
						/>
					</div>
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
							className={`clp-condition-btn ${condition === c ? "clp-condition-btn--active" : ""}`}
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
				<section className="clp-card">
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
							<div className="clp-upload-placeholder-img"><SubirImagenPosteo /></div>
							<span>Sube tus propias imágenes del ejemplar</span>
						</div>
						) : (
						<div className="clp-photo-previews">
							{photos.map((src, i) => (
							<img key={i} src={src} alt={`foto ${i + 1}`} className="clp-photo-thumb" />
							))}
							<div className="clp-upload-add">＋</div>
						</div>
						)}
					</label>
				</section>

				{/* Duración del préstamo */}
				<section className="clp-card">
					<h2 className="clp-card-title">Duración del préstamo</h2>
					<div className="clp-slider-wrapper">
						<input
						type="range"
						min={1}
						max={4}
						step={1}
						value={loanDuration}
						onChange={(e) => setLoanDuration(Number(e.target.value))}
						className="clp-slider"
						style={{ "--fill": `${sliderPercent}%` }}
						/>
						<span className="clp-slider-label">{currentDurationLabel}</span>
					</div>
				</section>
      		</div>

			{/* Vista previa */}
			<aside className="clp-preview-col">
				<p className="clp-preview-heading">Vista previa</p>
				<div className="clp-preview-card">
					<div className="clp-preview-book">
						<div className="clp-preview-cover" />
						<div className="clp-preview-info">
							<p className="clp-preview-book-title">
								{bookSearch.trim() || "Título del libro"}
							</p>
							<p className="clp-preview-author">Autor/es</p>
							<div className="clp-preview-meta">
								<span className="clp-preview-meta-item clp-preview-meta-item--check">
									<svg width="14" height="14" viewBox="0 0 16 16" fill="none">
										<circle cx="8" cy="8" r="7" stroke="var(--brand-primary)" strokeWidth="1.5" />
										<path d="M5 8l2 2 4-4" stroke="var(--brand-primary)" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
									</svg>
									Estado del ejemplar: {condition}
								</span>
								<span className="clp-preview-meta-item clp-preview-meta-item--clock">
									<svg width="14" height="14" viewBox="0 0 16 16" fill="none">
										<circle cx="8" cy="8" r="7" stroke="var(--gris-oscuro)" strokeWidth="1.5" />
										<path d="M8 5v3.5l2 1.5" stroke="var(--gris-oscuro)" strokeWidth="1.5" strokeLinecap="round" />
									</svg>
									Duración del préstamo: {currentDurationLabel}
								</span>
							</div>
						</div>
					</div>

					<hr className="clp-preview-divider" />

					{selectedDetails.length > 0 && (
						<>
						<p className="clp-preview-section-label">Detalles del ejemplar:</p>
						<div className="clp-preview-tags">
							{selectedDetails.map((d) => (
							<span key={d.id} className="clp-preview-tag">
								{d.label.replace("Ejemplar ", "").replace(".", "")}
							</span>
							))}
						</div>
						</>
					)}

					{photos.length > 0 && (
						<>
						<p className="clp-preview-section-label">Imágenes subidas:</p>
						<div className="clp-preview-images">
							{photos.slice(0, 4).map((src, i) => (
							<img key={i} src={src} alt="" className="clp-preview-img" />
							))}
						</div>
						</>
					)}

					{photos.length === 0 && selectedDetails.length === 0 && (
						<div className="clp-preview-images">
							<div className="clp-preview-img clp-preview-img--placeholder" />
							<div className="clp-preview-img clp-preview-img--placeholder" />
						</div>
					)}
				</div>

				<div className="clp-publish-btn">
					<Button variant="default" color="oscuro">Publicar</Button>
				</div>
			</aside>
    	</div>
  	);
}