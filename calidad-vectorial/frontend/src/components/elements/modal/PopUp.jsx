import { Button } from "../buttons/Button";
import "./PopUp.css";

export const PopUp = ({ children, onClick, titulo}) => {
    return (
        <div className="popup" onClick={onClick}>
            <div className="popup__card" onClick={(e) => e.stopPropagation()}>
                <div className="popup__card__close" onClick={onClick}>
                    &times; 
                </div>
                <div className="popup__card__text">
                    <div className="popup__card__text__title">
                        {titulo}
                    </div>
                    <div className="popup__card__text__content">
                        {children}
                    </div>
                </div>
                <div>
                    <Button type="button" variant="default" color="oscuro" onClick={onClick}>
                        Continuar
                    </Button>
                </div>
                
            </div>
        </div>
    );
};