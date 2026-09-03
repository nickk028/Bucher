import { useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./AuthPage.css";
import { InputLibro } from "../../elements/input/InputLibro";
import { Button } from "../../elements/buttons/Button";
import { LibroAnimado } from "../../elements/animatedbook/LibroAnimado";
import { loginRequest, validarSeguridadPassword } from "../../utils/LoginUtils";
import { postData } from "../../utils/FetchUtils";

export const AuthPage = () => {
    const navigate = useNavigate();
    const [pagina, setPagina] = useState("login");
    const [hasAnimated, setHasAnimated] = useState(false);

    // Estado del formulario de login
    const [loginUsername, setLoginUsername] = useState("");
    const [loginPassword, setLoginPassword] = useState("");
    const [isLoginDisabled, setIsLoginDisabled] = useState(false);

    // Estado del formulario de registro (simplificado: solo email/password)
    const [registerUsername, setRegisterUsername] = useState("");
    const [registerPassword, setRegisterPassword] = useState("");
    const [registerVerificationPassword, setRegisterVerificationPassword] = useState("");
    const [isRegisterDisabled, setIsRegisterDisabled] = useState(false);

    // Mensaje / animación de los libros
    const [mensaje, setMensaje] = useState("");
    const [ojoQueHabla, setOjoQueHabla] = useState(null);

    const loginControllerRef = useRef(null);
    const registerControllerRef = useRef(null);

    // Aborta requests activos si el componente se desmonta
    useEffect(() => {
        return () => {
            if (loginControllerRef.current) loginControllerRef.current.abort();
            if (registerControllerRef.current) registerControllerRef.current.abort();
        };
    }, []);

    const cambiarPagina = (nuevaPagina) => {
        setHasAnimated(true);
        setPagina(nuevaPagina);
        setMensaje("");
        setOjoQueHabla(null);
        navigate(nuevaPagina === "register" ? "/register" : "/login");
    };

    const mostrarError = (texto) => {
        setMensaje(texto);
        setOjoQueHabla(Math.floor(Math.random() * 3));
    };

    // Extrae un mensaje legible de una respuesta de error del backend
    const extraerMensajeError = async (respond) => {
        if (respond.message === "Error de conexión") {
            return "Error de conexión";
        }
        try {
            const jsonResponse = await respond.json();
            if (jsonResponse.errores) {
                return Object.values(jsonResponse.errores).join(". ");
            } else if (jsonResponse.title) {
                return jsonResponse.title;
            } else if (jsonResponse.detail) {
                return jsonResponse.detail;
            }
            return JSON.stringify(jsonResponse);
        } catch (err) {
            return "Error: " + err.message;
        }
    };

    const handleLogin = async (evento) => {
        evento.preventDefault();

        if (loginControllerRef.current) {
            loginControllerRef.current.abort();
            loginControllerRef.current = null;
        }
        const controller = new AbortController();
        loginControllerRef.current = controller;

        setMensaje("");
        setOjoQueHabla(null);
        setIsLoginDisabled(true);

        try {
            const respond = await loginRequest(
                { username: loginUsername, password: loginPassword },
                controller.signal
            );

            if (respond.ok) {
                navigate("/index");
                return;
            }

            const texto = await extraerMensajeError(respond);
            mostrarError(texto);
        } catch (err) {
            if (err.name !== "AbortError") {
                mostrarError("Error: " + err.message);
            }
        } finally {
            if (loginControllerRef.current === controller) loginControllerRef.current = null;
            setIsLoginDisabled(false);
        }
    };

    const handleRegister = async (evento) => {
        evento.preventDefault();

        if (registerPassword !== registerVerificationPassword) {
            mostrarError("Las contraseñas deben ser iguales.");
            return;
        }

        if (validarSeguridadPassword(registerPassword) !== 5) {
            mostrarError("La contraseña no es del todo segura.");
            return;
        }

        if (registerControllerRef.current) {
            registerControllerRef.current.abort();
            registerControllerRef.current = null;
        }
        const controller = new AbortController();
        registerControllerRef.current = controller;

        setMensaje("");
        setOjoQueHabla(null);
        setIsRegisterDisabled(true);

        try {
            const respond = await postData(
                "usuario/registrar",
                {
                    username: registerUsername,
                    password: registerPassword,
                    verificationPassword: registerVerificationPassword
                },
                controller.signal
            );

            if (respond.ok) {
                // Login automático tras registrarse
                await loginRequest({ username: registerUsername, password: registerPassword });
                navigate("/index");
                return;
            }

            const texto = await extraerMensajeError(respond);
            mostrarError(texto);
        } catch (err) {
            if (err.name !== "AbortError") {
                mostrarError("Error: " + err.message);
            }
        } finally {
            if (registerControllerRef.current === controller) registerControllerRef.current = null;
            setIsRegisterDisabled(false);
        }
    };

    return (
        <div className="auth-page">
            <main className="auth-page__book">
                <section className="auth-page__book__page auth-page__book__page--static auth-page__book__page--izquierda">
                    <h1>Bücher</h1>
                    <p>Inicia sesión para llevar tu lectura al máximo</p>
                    <div className="auth-page__book__page--izquierda__buchi">
                        <LibroAnimado variant="büchi" color="verde-claro">)</LibroAnimado>
                    </div>
                </section>

                <section className={`auth-page__book__page auth-page__book__page--derecha auth-page__book__page--flip flip--${hasAnimated ? `${pagina}` : ""}`}>
                    <section className="auth-page__book__page--flip__front">
                        <h2>Iniciar sesión</h2>
                        <form className="form" onSubmit={handleLogin}>
                            <InputLibro
                                type="text"
                                name="username"
                                value={loginUsername}
                                onChange={(e) => setLoginUsername(e.target.value)}
                                autoComplete="on"
                            >
                                Email:
                            </InputLibro>
                            <InputLibro
                                type="password"
                                name="password"
                                value={loginPassword}
                                onChange={(e) => setLoginPassword(e.target.value)}
                                autoComplete="on"
                            >
                                Contraseña:
                            </InputLibro>
                            <Button type="submit" variant="default" color="oscuro" isDisabled={isLoginDisabled}>
                                Aceptar
                            </Button>
                        </form>
                        <p onClick={() => cambiarPagina("register")}>¿No tienes una cuenta? ¡Crea una!</p>
                    </section>
                    <section className="auth-page__book__page--flip__back">
                        <h2>Registrarse</h2>
                        <form className="form" onSubmit={handleRegister}>
                            <InputLibro
                                type="text"
                                name="username"
                                value={registerUsername}
                                onChange={(e) => setRegisterUsername(e.target.value)}
                                autoComplete="on"
                            >
                                Email:
                            </InputLibro>
                            <InputLibro
                                type="password"
                                name="password"
                                value={registerPassword}
                                onChange={(e) => setRegisterPassword(e.target.value)}
                                autoComplete="on"
                            >
                                Contraseña:
                            </InputLibro>
                            <InputLibro
                                type="password"
                                name="verificationPassword"
                                value={registerVerificationPassword}
                                onChange={(e) => setRegisterVerificationPassword(e.target.value)}
                            >
                                Confirmar Contraseña:
                            </InputLibro>
                            <Button type="submit" variant="default" color="oscuro" isDisabled={isRegisterDisabled}>
                                Aceptar
                            </Button>
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
                    <div className="auth-page__book__page--derecha__buchi">
                        <LibroAnimado variant="büchi" color="verde-claro">)</LibroAnimado>
                    </div>
                </section>
            </main>

            <div className="auth-page__grupo-ojos" style={{ display: mensaje ? "block" : "none" }}>
                <LibroAnimado variant="grande" color="rojo" mensaje={mensaje} mostrarMensaje={ojoQueHabla === 0}>
                    Bü
                </LibroAnimado>
                <LibroAnimado variant="chico" color="azul" mensaje={mensaje} mostrarMensaje={ojoQueHabla === 1}>
                    ch
                </LibroAnimado>
                <LibroAnimado variant="medio" color="amarillo" mensaje={mensaje} mostrarMensaje={ojoQueHabla === 2}>
                    er
                </LibroAnimado>
            </div>
        </div>
    );
}