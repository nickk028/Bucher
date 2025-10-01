import React from 'react'
import { Link } from "react-router-dom";
import '../../elements/global.css'
import './SobreNosotros.css';

export const SobreNosotros = () => {
    return (
        <section className="hero">
            <div className="hero__content">
                <h1 className="hero__title">Bücher</h1>
                <p className="hero__subtitle">
                Lleva tu lectura al siguiente nivel,
                <br/>
                que lo bueno se comparta</p>
            </div>
            <div className="hero__image"></div>
    </section>

    /*
    <div>
        aboutus
        <Link to="/login">
            <button>Ir al Login</button>
        </Link>
    </div>*/
    )
}