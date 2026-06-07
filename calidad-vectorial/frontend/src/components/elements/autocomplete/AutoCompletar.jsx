import { useState, useEffect } from "react";
import { Input } from "../input/Input";
import "./AutoCompletar.css";
import { getData } from "../../utils/FetchUtils";

export function Autocompletar({ urlFetch, tipo, value: valorExterno, onChange, onSelect, imgHeight, imgWidth, variant, ...props }) {
	const [valorInterno, setValorInterno] = useState("");
	const [showList, setShowList] = useState(false);
	const [optFiltrados, setOptFiltrados] = useState([]);

	// Detectar si el componente está siendo controlado desde afuera
	const isControlled = valorExterno !== undefined;

	// El valor actual: externo si está controlado, interno si no lo está (siempre deberia estarlo)
	const value = String(isControlled ? (valorExterno ?? "") : valorInterno);

	if (isControlled && typeof onChange !== "function") {
		console.warn("Autocompletar: 'value' fue proporcionado pero falta 'onChange'. El componente está en modo controlado y no podrá actualizarse desde dentro.");
	}

	const setValue = isControlled
		? (newVal) => {
			if (typeof onChange === "function") {
				onChange({ target: { value: newVal } });
			}
		}
		: setValorInterno;

	// Filtrado seguro: coercionar opciones a string antes de comparar
	const valorNormalizado = (value ?? "").toString();

	const handleSelect = (opcion) => {
		setValue(opcion.titulo);
		setShowList(false);

		if (typeof onSelect === "function") {
			onSelect(opcion);
		}
	};

	useEffect(() => {
		if (!value || value.trim().length < 1) {
			setOptFiltrados([]);
			setShowList(false);
			return;
		}

		const controller = new AbortController();

		const timer = setTimeout(async () => {
			try {
				const respond = await getData(urlFetch + value, controller.signal);

				if (!respond.ok) {
					throw new Error("Error en la respuesta del servidor: " + respond.status + " " + respond.statusText);
				}

				const data = (await respond.json());

				setOptFiltrados(data);

				setShowList(true);
			} catch (err) {
				if (err.name !== "AbortError") {
					console.error(err);
				}
			}
		}, 500);

		return () => {
			clearTimeout(timer);
			controller.abort();
		};

	}, [value, urlFetch]);

	return (
		<div className="autocomplete">
			<Input
				type="text"
				variant={variant}
				value={value}
				onChange={(e) => {
					setValue(e.target.value);
					setShowList(true);
				}}
				onFocus={() => setShowList(true)}
				onBlur={() => setTimeout(() => setShowList(false), 100)}
				{...props}
			/>

			{showList && optFiltrados.length > 0 && (
				<ul className="autocomplete__options">
					<>
						{optFiltrados.map((opcion) => (
							<li className="autocomplete__options__item" key={opcion.id} onMouseDown={() => handleSelect(opcion)}>
								{opcion.urlFoto && <img src={opcion.urlFoto} alt="Imagen" height={imgHeight} width={imgWidth}/>}
								{opcion.titulo}
							</li>
						))}
					</>
				</ul>
			)}
		</div>
	);
}