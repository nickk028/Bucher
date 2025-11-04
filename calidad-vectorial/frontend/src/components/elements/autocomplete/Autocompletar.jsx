import { useState } from "react";
import { Input } from "../input/Input";
import "./Autocompletar.css";

export function Autocompletar({ options = [], tipo, value: valorExterno, onChange, imgHeight, imgWidth, maxSuggestions = 100, ...props }) {
	const [valorInterno, setValorInterno] = useState("");
	const [showList, setShowList] = useState(false);

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
	const optFiltrados =
		valorNormalizado.trim() === ""
			? options.slice(0, maxSuggestions)
			: tipo === "simple" ? options.filter((opt) => String(opt ?? "").toLowerCase().includes(valorNormalizado.toLowerCase())).slice(0, maxSuggestions)
				: tipo == "doble" ? options.filter((opt) => String(opt[1] ?? "").toLowerCase().includes(valorNormalizado.toLowerCase())).slice(0, maxSuggestions)
				: options.slice(0, maxSuggestions);

	const handleSelect = (option) => {
			setValue(option);
			setShowList(false);
	};

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
					{tipo == "simple" && (
						<>
						{optFiltrados.map((opcion, i) => (
							<li className="autocomplete__options__item" key={i} onMouseDown={() => handleSelect(opcion)}>
								{opcion}
							</li>
						))}
						</>
					)}
					{tipo == "doble" &&(
						<>
						{optFiltrados.map((opcion, i) => (
							<li className="autocomplete__options__item" key={i} onMouseDown={() => handleSelect(opcion[1])}>
								<img src={opcion[0]} alt="Imagen libro" height={imgHeight} width={imgWidth}/>
								{opcion[1]}
							</li>
						))}
						</>
					)}
				</ul>
			)}
		</div>
	);
}
