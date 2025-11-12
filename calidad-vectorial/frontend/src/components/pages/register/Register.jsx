import { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { loginRequest, validarSeguridadPassword } from "../../utils/LoginUtils";
import { useBook } from "../../../context/LibroContexto";
import "./Register.css";
import { AuthBox } from "../../elements/authbox/AuthBox";
import { Button } from "../../elements/buttons/Button";
import { Input } from "../../elements/input/Input";
import { postData } from "../../utils/FetchUtils";
import { LibroAnimado } from "../../elements/animatedbook/LibroAnimado";
import { BarraPassword } from "../../elements/bar/BarraPassword";

export const Register = () => {
    const navigate = useNavigate();
    const controllerRef = useRef(null);

    const { libroMensaje, setLibroMensaje } = useBook();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [verificationPassword, setVerificationPassword] = useState("");
    const [nickname, setNickname] = useState("");
    const [fechaNacimiento, setfechaNacimiento] = useState("");
    const [direccion, setDireccion] = useState("");
    const [pisoDept, setPisoDept] = useState("");
    const [codigoPostal, setcodigoPostal] = useState("");
    const yearActual = new Date().getFullYear();

    const [error, setError] = useState("");
    const [paso, setPaso] = useState(1);
    const [isDisabled, setIsDisabled] = useState(true);
    const [ojoQueHabla, setOjoQueHabla] = useState(null);

    useEffect(() => {
        switch (paso) {
            case 1:
                setIsDisabled(
                    !username.trim() ||
                    !password.trim() ||
                    !verificationPassword.trim()
                );
                break;
            case 2:
                setIsDisabled(
                    !nickname.trim() ||
                    !fechaNacimiento.trim()
                );
                break;
            case 3:
                setIsDisabled(
                    !direccion.trim() ||
                    !codigoPostal.trim()
                );
                break;
            default:
                setIsDisabled(true);
        }
    }, [
        paso,
        username,
        password,
        verificationPassword,
        nickname,
        fechaNacimiento,
        direccion,
        codigoPostal
    ]
    );

    // cleanup on unmount: abort any in-flight request
    useEffect(() => {
        return () => {
            if (controllerRef.current) {
                controllerRef.current.abort();
            }
        };
    }, []);

    useEffect(() => {
        setOjoQueHabla(Math.floor(Math.random() * 3));
    }, [libroMensaje])

    const handleRegister = async (evento) => {
        evento.preventDefault();
        if (controllerRef.current) {
			controllerRef.current.abort();
			controllerRef.current = null;
		}
		// Nuevo controller para la nueva request
		const controller = new AbortController();
		controllerRef.current = controller; // guarda el controller en la referencia
        setLibroMensaje("");

        try {
            const respond = await postData("usuario/registrar", {
                username, password, verificationPassword, nickname, direccion, piso: pisoDept, codigoPostal
            },
                controller.signal
            );

            if (respond.ok) {
                await loginRequest({username, password});
                // Login automatico sin guardar respond
                navigate("/index");
            } else {
                const text = await respond.json();
                setPaso(1);
                setLibroMensaje(text.detail);
                setOjoQueHabla(Math.floor(Math.random() * 3));
            }
            setOjoQueHabla(Math.floor(Math.random() * 3));
        } catch (error) {
            setLibroMensaje("Error de conexión");
            setOjoQueHabla(Math.floor(Math.random() * 3));
        }
    };

    return (
        <div className="body-register">
            <div className="body-login__grupo-ojos">
                <LibroAnimado variant="grande" color="rojo" mensaje={libroMensaje} mostrarMensaje={ojoQueHabla === 0}>
                    Bü
                </LibroAnimado>
                <LibroAnimado variant="chico" color="azul" mensaje={libroMensaje} mostrarMensaje={ojoQueHabla === 1}>
                    ch
                </LibroAnimado>
                <LibroAnimado variant="medio" color="amarillo" mensaje={libroMensaje} mostrarMensaje={ojoQueHabla === 2}>
                    er
                </LibroAnimado>
            </div>
            {paso == 1 && (
                <AuthBox titulo="Registrarse"
                botonDer={
                    <Button variant="default" color="oscuro" isDisabled={isDisabled}
                    onClick={() => {
                            if (verificationPassword === password) {
                                if (validarSeguridadPassword(password) === 5) {
                                    setPaso(paso + 1)
                                } else {
                                    setLibroMensaje("La contraseña no es del todo segura.");
                                }
                            } else {
                                setLibroMensaje("Las contraseñas deben ser iguales.");
                            }
                        }
                    }>
                        Siguiente
                    </Button>
                }
                linkExtra={
                    <Link to="/login">¿Ya tienes una cuenta? ¡Inicia sesión!</Link>
                }>
                    <Input type="text" value={username} name="username" onChange={e => setUsername(e.target.value)} autoComplete="on">Email</Input>
                    <BarraPassword password={password}></BarraPassword>
                    <Input type="password" value={password} name="password" onChange={e => setPassword(e.target.value)} autoComplete="on">Contraseña</Input>
                    <Input type="password" value={verificationPassword} name="verificationPassword" onChange={e => setVerificationPassword(e.target.value)}>Confirmar contraseña</Input>
                </AuthBox>
            )}

            {paso == 2 && (
                <AuthBox titulo="Ya casi..."
                botonDer={
                    <Button variant="default" color="oscuro" isDisabled={isDisabled} onClick={() => {if (parseInt(fechaNacimiento.split("-")) < yearActual && parseInt(fechaNacimiento.split("-")) > yearActual - 100) {setPaso(paso + 1)} else {setLibroMensaje("La fecha de nacimiento debe ser válida.")}}}>Siguiente</Button>
                }
                botonIzq={
                    <Button variant="default" color="oscuro" onClick={() => setPaso(paso - 1)}>Atrás</Button>
                }>
                    <Input type="text" value={nickname} name="nickname" onChange={e => setNickname(e.target.value)}>Nombre de usuario</Input>
                    <Input type="date" value={fechaNacimiento} name="fechaNacimiento" onChange={e => setfechaNacimiento(e.target.value)}>Fecha de nacimiento</Input>
                </AuthBox>
            )}

            {paso == 3 && (
                <AuthBox titulo="Lo último..." onSubmit={handleRegister}
                botonDer={
                    <Button type="submit" variant="default" color="oscuro" isDisabled={isDisabled}>Aceptar</Button>
                }
                botonIzq={
                    <Button variant="default" color="oscuro" onClick={() => setPaso(paso - 1)}>Atrás</Button>
                }>
                    <Input type="text" value={direccion} name="direccion" onChange={e => setDireccion(e.target.value)}>Dirección</Input>
                    <Input type="text" value={pisoDept} name="pisoDept" onChange={e => setPisoDept(e.target.value)}>Piso / Departamento</Input>
                    <Input type="text" value={codigoPostal} name="codigoPostal" onChange={e => setcodigoPostal(e.target.value)}>Código postal </Input>
                </AuthBox>
            )}
		</div>
    );
};