import { PolarAngleAxis, RadialBar, RadialBarChart, ResponsiveContainer } from 'recharts';
import './Grafico.css';

const COLOR_PROGRESO = '#0E675F';

export const GraficoCircular = ({ data }) => {
    const porcentaje = Math.min(Math.max(Number(data) || 0, 0), 100);

    return (
        <div className="grafico-circular">
            <ResponsiveContainer width="100%" height="100%">
                <RadialBarChart
                    data={[{ valor: porcentaje }]}
                    innerRadius="56%"
                    outerRadius="100%"
                    startAngle={90}
                    endAngle={-270}
                >
                    <PolarAngleAxis type="number" domain={[0, 100]} tick={false} />
                    <RadialBar
                        dataKey="valor"
                        fill={COLOR_PROGRESO}
                        cornerRadius="50%"
                        background='#E4EBE5'
                    />
                </RadialBarChart>
            </ResponsiveContainer>
            <p className="grafico-circular__valor">
                {porcentaje.toLocaleString('es-AR', { maximumFractionDigits: 1 })} %
            </p>
        </div>
    );
};