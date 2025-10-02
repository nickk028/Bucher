import React from 'react'
import './AuthBox.css';
import { Button } from "../buttons/Button";

export const AuthBox = ({ children, title="Título del cuadro" }) => {
    return (
        <section className='auth-box'>
            <h1 className='auth-box__title'>{title}</h1>
            <div className='auth-box__content'>
                <div className='auth-box__content__inputs'>
                    {children}
                </div>

                <div className='auth-box__content__button'>
                    <Button variant="default" color="oscuro">Aceptar</Button>
                </div>
            </div>
            <h1 className='auth-box__footer'>Bücher</h1>
        </section>
    )
}