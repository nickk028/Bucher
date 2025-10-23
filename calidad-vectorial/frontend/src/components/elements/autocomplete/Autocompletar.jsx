import { useState } from "react";
import { Input } from "../input/Input";

export function Autocompletar({ options = [], value: valorExterno, onChange, maxSuggestions = 3, ...props }) {
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
	const normalizedValue = (value ?? "").toString();
	const filtered =
		normalizedValue.trim() === ""
			? []
			: options
				  .filter((opt) => String(opt ?? "").toLowerCase().includes(normalizedValue.toLowerCase()))
				  .slice(0, maxSuggestions);

	const handleSelect = (option) => {
		setValue(option);
		setShowList(false);
	};

	return (
		<div>
			<Input
				type="text"
				className="form-control"
				value={value}
				onChange={(e) => {
					setValue(e.target.value);
					setShowList(true);
				}}
				onFocus={() => setShowList(true)}
				onBlur={() => setTimeout(() => setShowList(false), 100)}
				{...props}
			/>

			{showList && filtered.length > 0 && (
				<ul>
					{filtered.map((opt, i) => (
						<li key={i} onMouseDown={() => handleSelect(opt)}>
							{opt}
						</li>
					))}
				</ul>
			)}
		</div>
	);
}
