import React from 'react'
import { Link } from "react-router-dom";
import { Button } from '../../elements/buttons/Button';
import { ButtonGroup } from '../../elements/buttons/ButtonGroup';
import { Footer } from '../../elements/footer/Footer';
import '../../elements/global.css'
import './SobreNosotros.css';

export const SobreNosotros = () => {
    return (
        <div className='body-sobre-nosotros'>
            <section className="hero">
                <div className="hero__content">
                    <h1 className="hero__title">Bücher</h1>
                    <p className="hero__subtitle">
                    Lleva tu lectura al siguiente nivel,
                    <br/>
                    que lo bueno se comparta.</p>
                </div>
                <div className="hero__image"></div>
        </section>
        <ButtonGroup>
            <Button variant='solapa' color='claro'><Link to="/login">Iniciar Sesión</Link></Button>
            <Button variant='solapa' color='oscuro'><Link to="/register">Registrarse</Link></Button>
        </ButtonGroup>

        <Footer></Footer>
    </div>
    )
}