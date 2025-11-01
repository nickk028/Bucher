import "./PopUp.css";

export const PopUp = ({ children, onClose }) => {
    const handleClose = () => {
        
    };

    return (
        <div className="popup" onClick={onClose}>
            <div className="popup__content" onClick={(e) => e.stopPropagation()}>
                {children}
            </div>
        </div>
    )
}