import "./PopUp.css";

export const PopUp = ({ children, onClick }) => {
    return (
        <div className="popup" onClick={onClick}>
            <div className="popup__content" onClick={(e) => e.stopPropagation()}>
                <div className="popup__content__close" onClick={onClick}>
                    &times; 
                </div>
                <div className="popup__content__text">
                    {children}
                </div>
            </div>
        </div>
    );
};