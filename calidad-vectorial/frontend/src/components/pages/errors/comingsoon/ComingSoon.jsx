import './ComingSoon.css';
import { useNavigate } from 'react-router-dom';
import { useFetch } from '../../../utils/FetchUtils';
import { Button } from '../../../elements/buttons/Button';

export const ComingSoon = () => {
    const navigate = useNavigate();
    return (
        <div className="coming-soon">
            <div className="coming-soon__box">
                <h1 className="coming-soon__title">Próximamente</h1>
                <p className="coming-soon__subtitle">Este capítulo aún se esta escribiendo.</p>
                <Button variant="default" color="oscuro" onClick={() => navigate('/index')}>
                    Volver al inicio
                </Button>
            </div>
        </div>
    );
};
