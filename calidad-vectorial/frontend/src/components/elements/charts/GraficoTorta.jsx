import { Cell, Pie, PieChart, ResponsiveContainer, Tooltip } from 'recharts';
import './Grafico.css';

const COLORES_PORCIONES = ['#409495', '#0E675F', '#00B282', '#08795A', '#004740', '#00BF63'];

const RADIANES = Math.PI / 180;

const formatearPorcentaje = (porcentaje) =>
    `${(porcentaje * 100).toLocaleString('es-AR', { maximumFractionDigits: 1 })}%`;

const EtiquetaPorcion = ({ cx, cy, midAngle, outerRadius, percent, name }) => {
    const coseno = Math.cos(-midAngle * RADIANES);
    const seno = Math.sin(-midAngle * RADIANES);
    const distancia = outerRadius + 22 + Math.abs(coseno) * 26;

    return (
        <text
            className="grafico-torta__etiqueta"
            x={cx + distancia * coseno}
            y={cy + distancia * seno}
            textAnchor="middle"
            dominantBaseline="central"
        >
            <tspan x={cx + distancia * coseno} dy="-0.6em">
                {name}
            </tspan>
            <tspan x={cx + distancia * coseno} dy="1.2em">
                {formatearPorcentaje(percent)}
            </tspan>
        </text>
    );
};

export const GraficoTorta = ({ data, claveEtiqueta = 'nombre', claveValor = 'valor' }) => {
    return (
        <div className="grafico-torta">
            <ResponsiveContainer width="100%" height="100%">
                <PieChart margin={{ top: 20, right: 20, bottom: 20, left: 20 }}>
                    <Pie
                        data={data}
                        dataKey={claveValor}
                        nameKey={claveEtiqueta}
                        startAngle={90}
                        endAngle={-270}
                        outerRadius="65%"
                        strokeWidth={0}
                        label={EtiquetaPorcion}
                        labelLine={false}
                    >
                        {data.map((item, indice) => (
                            <Cell
                                key={item[claveEtiqueta]}
                                fill={COLORES_PORCIONES[indice % COLORES_PORCIONES.length]}
                            />
                        ))}
                    </Pie>
                    <Tooltip />
                </PieChart>
            </ResponsiveContainer>
        </div>
    );
};