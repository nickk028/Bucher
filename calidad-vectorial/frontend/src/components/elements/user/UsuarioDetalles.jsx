import React from 'react'
import './UsuarioDetalles.css';

export const UsuarioDetalles = ({ children, nombre }) => {
    return (
        <div className='card-user'>
            <div className='card-user__content'>
                <h1 className='card-user__content__name'>{nombre}</h1>
                <p className='card-user__content__description'>{children}</p>
            </div>
            
        </div>
    )
}