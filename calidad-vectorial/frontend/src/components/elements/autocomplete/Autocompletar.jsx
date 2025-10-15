import { useState } from "react";

export function Autocompletar({options = [], value: valorExterno, onChange, maxSuggestions = 3, ...props}) {
	const [valorInterno, setValorInterno] = useState("");
	const [showList, setShowList] = useState(false);

	// Detectar si el componente está siendo controlado desde afuera
	const isControlled = valorExterno !== undefined;

	// Si se proporciona value, se usa como valor controlado, si no, se usa el estado interno
	const value = isControlled ? valorExterno : valorInterno;
	// Si el componente está controlado pero no se pasó onChange, avisar al desarrollador
	if (isControlled && typeof onChange !== "function") {
		// eslint-disable-next-line no-console
		console.warn("Autocompletar: 'value' fue proporcionado pero falta 'onChange'. El componente está en modo controlado y no podrá actualizarse desde dentro.");
	}

	// Define setValue
	// * Si está controlado, usar onChange
	// * Si no está controlado, usar setValorInterno
	const setValue = isControlled ? (onChange ?? (() => {})) : setValorInterno;

	// Si el valor está vacío, no muestra sugerencias
	const filtered =
		value.trim() === "" ? []
			: options.filter((opt) =>
				opt.toLowerCase().includes(value.toLowerCase())
			)
			.slice(0, maxSuggestions); // Maximo de sugrerencias definido

	// Maneja la selección de una opción
	const handleSelect = (option) => {
		setValue(option);
		setShowList(false);
	};

  return (
    <div style={{ position: "relative" }}>
		<input type="text" className="form-control" value={value} onChange={(e) => { setValue(e.target.value); setShowList(true); }}
		onFocus={() => setShowList(true)} onBlur={() => setTimeout(() => setShowList(false), 100)} {...props} />

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
