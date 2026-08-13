import { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import "./AuthPage.css";

export const AuthPage = () => {
    const [pagina, setPagina] = useState("login");
    const [hasAnimated, setHasAnimated] = useState(false);

    const cambiarPagina = (pagina) => {
        setHasAnimated(true);
        setPagina(pagina);
    }

    return (
        <div className="auth-page">
            <main className="auth-page__book">
                <section className="auth-page__book__page auth-page__book__page--static auth-page__book__page--izquierda">
                    <h1>Bücher</h1>
                    <p>Inicia sesión para llevar tu lectura al máximo</p>
                </section>

                <section className={`auth-page__book__page auth-page__book__page--derecha auth-page__book__page--flip flip--${hasAnimated ? `${pagina}` : ""}`}>
                    <section className="auth-page__book__page--flip__front">
                        <h2>Iniciar sesión</h2>
                        <form action="" className="form">
                            <div>
                                <label htmlFor="">Email:</label>
                                <input type="text" name="" id="" />
                            </div>
                            <div>
                                <label htmlFor="">Contraseña:</label>
                                <input type="text" name="" id="" />
                            </div>
                        </form>
                        <p onClick={() => cambiarPagina("register")}>¿No tienes una cuenta? ¡Crea una!</p>
                    </section>
                    <section className="auth-page__book__page--flip__back">
                        <h2>Registrarse</h2>
                        <form action="" className="form">
                            <div>
                                <label htmlFor="">Email:</label>
                                <input type="text" name="" id="" />
                            </div>
                            <div>
                                <label htmlFor="">Contraseña:</label>
                                <input type="text" name="" id="" />
                            </div>
                            <div>
                                <label htmlFor="">Confirmar contraseña:</label>
                                <input type="text" name="" id="" />
                            </div>
                        </form>
                        <p onClick={() => cambiarPagina("login")}>¿Ya tienes una cuenta? ¡Inicia sesión!</p>
                    </section>
                    <span className="auth-page__book__page--flip__fold" onClick={() => pagina == "login" ? cambiarPagina("register") : cambiarPagina("login")}>
                        <span className="auth-page__book__page--flip__fold-shadow"></span>
                        <span className="auth-page__book__page--flip__fold-paper"></span>
                        <span className="auth-page__book__page--flip__fold-crease"></span>
                    </span>
                </section>

                <span className="auth-page__book__borde" />

                <section className="auth-page__book__page auth-page__book__page--static auth-page__book__page--derecha">
                    <h1>Bücher</h1>
                    <p>Crea una cuenta para adentrarte en el mundo de la lectura</p>
                </section>
            </main>
            
		</div>
    );
}