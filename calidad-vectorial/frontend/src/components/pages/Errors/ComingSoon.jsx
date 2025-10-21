import './ComingSoon.css'
import { Link } from 'react-router-dom';
export const ComingSoon = () => {
    return (  
        <div className="coming-soon">
            <div className="cs-box">
                <h1>Coming Soon</h1>
                <Link to="/index" className="cs-link">Volver al Inicio</Link>
            </div>
        </div>
    )
}
