import React from 'react'
import './Footer.css';

export const Footer = () => {
    return (
        <footer className='footer'>
            <h1 className='footer__title'>Bücher</h1>
            <div className='footer__content'>
                <div>
                    <p>Registrarse</p>
                    <p>Iniciar Sesión</p>
                </div>

                <div>
                    <p>Calidad Vectorial</p>
                    <p>Ins. Ind. Luis A. Huergo</p>
                </div>
            </div>
        </footer>
    )
    }