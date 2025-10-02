import React from 'react'
import './AuthBox.css';
import { Button } from "../buttons/Button";

export const AuthBox = ({ children, titulo="Título del cuadro", onSubmit }) => {
    return (
        <section className='auth-box'>
            <h1 className='auth-box__title'>{titulo}</h1>
            <form className='auth-box__content' onSubmit={onSubmit}>
                <div className='auth-box__content__inputs'>
                    {children}
                </div>

                <div className='auth-box__content__button'>
                    <Button variant="default" color="oscuro">Aceptar</Button>
                </div>
            </form>
            <h1 className='auth-box__footer'>Bücher</h1>
        </section>
    )
}