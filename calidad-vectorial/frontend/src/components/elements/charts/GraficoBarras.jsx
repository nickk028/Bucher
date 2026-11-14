import { Bar, BarChart, Cell, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import './Grafico.css';

const COLORES_BARRAS = [
    '#0E675F',
    '#014D44',
    '#0097B2',
    '#004740',
    '#00B282',
    '#0E675F',
    '#409495',
    '#34B8CB',
];

const RADIO_PUNTAS = [8, 8, 0, 0];

export const GraficoBarras = ({ data, claveEtiqueta = 'nombre', claveValor = 'valor' }) => {
    return (
        <div className="grafico-barras">
            <ResponsiveContainer width="100%" height="100%">
                <BarChart data={data} margin={{ top: 10, right: 10, bottom: 0, left: 0 }}>
                    <XAxis
                        dataKey={claveEtiqueta}
                        tickLine={false}
                        axisLine={{ stroke: 'var(--border-secondary)' }}
                        tick={{ fill: 'var(--text-secondary)', fontSize: 12 }}
                    />
                    <YAxis
                        tickLine={false}
                        axisLine={{ stroke: 'var(--border-secondary)' }}
                        tick={{ fill: 'var(--text-secondary)', fontSize: 12 }}
                    />
                    <Tooltip cursor={{ fill: 'transparent' }} />
                    <Bar dataKey={claveValor} radius={RADIO_PUNTAS}>
                        {data.map((item, indice) => (
                            <Cell
                                key={item[claveEtiqueta]}
                                fill={COLORES_BARRAS[indice % COLORES_BARRAS.length]}
                            />
                        ))}
                    </Bar>
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};