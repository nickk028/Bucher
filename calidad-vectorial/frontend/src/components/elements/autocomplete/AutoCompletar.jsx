import { useState, useEffect } from "react";
import { Input } from "../input/Input";
import "./AutoCompletar.css";
import { getData } from "../../utils/FetchUtils";

export function Autocompletar({ urlFetch, tipo, value: valorExterno, onChange, imgHeight, imgWidth, maxSuggestions = 100, ...props }) {
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

	const handleSelect = (option) => {
		setValue(option);
		setShowList(false);
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
				const respond = await getData(urlFetch, controller.signal);

				if (!respond.ok) {
					throw new Error("Error en la respuesta del servidor: " + respond.status + " " + respond.statusText);
				}

				const data = (await respond.json()).slice(0, maxSuggestions);

				setOptFiltrados(data.map(obj => [obj.urlFoto ?? null, obj.titulo]));

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
						{optFiltrados.map((opcion, i) => (
							<li className="autocomplete__options__item" key={i} onMouseDown={() => handleSelect(opcion[1])}>
								{opcion[0] && <img src={opcion[0]} alt="Imagen" height={imgHeight} width={imgWidth}/>}
								{opcion[1]}
							</li>
						))}
					</>
				</ul>
			)}
		</div>
	);
}