import React from 'react'
import { Button } from '../../elements/buttons/Button';
import { ButtonGroup } from '../../elements/buttons/ButtonGroup';
import { Footer } from '../../elements/footer/Footer';
import '../../elements/global.css';
import './SobreNosotros.css';

export const SobreNosotros = () => {
    return (
        <>
        <div className='body-sobre-nosotros'>
            <section className="body-sobre-nosotros__hero">
                <div className="body-sobre-nosotros__hero__content">
                    <h1 className="body-sobre-nosotros__hero__title">Bücher</h1>
                    <p className="body-sobre-nosotros__hero__subtitle">
                    Lleva tu lectura al siguiente nivel,
                    <br/>
                    que lo bueno se comparta.</p>
                </div>
                <div className="body-sobre-nosotros__hero__image"></div>
        </section>
        <ButtonGroup>
            <Button variant='solapa' color='claro' to="/login">Iniciar sesión</Button>
            <Button variant='solapa' color='oscuro' to='/register'>Registrarse</Button>
        </ButtonGroup>
        </div>
        <Footer></Footer>
    
    </>
    )
}