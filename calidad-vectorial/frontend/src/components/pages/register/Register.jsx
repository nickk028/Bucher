import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "../../utils/LoginUtils";
import './Register.css';
import { AuthBox } from "../../elements/authbok/AuthBox";
import { Button } from "../../elements/buttons/Button";
import { Input } from "../../elements/input/Input";

export const Register = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [verificationPassword, setVerificationPassword] = useState("");
    const [nickname, setNickname] = useState("");
    const [fechaNacimiento, setfechaNacimiento] = useState("");
    const [direccion, setDireccion] = useState("");
    const [pisoDept, setPisoDept] = useState("");
    const [codigoPostal, setcodigoPostal] = useState("");

    const [message, setMessage] = useState("");
    const [paso, setPaso] = useState(1);
    const [isDisabled, setIsDisabled] = useState(true);

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
    ]);

    const handleRegister = async (evento) => {
        evento.preventDefault();
        try {
            const respond = await fetch("http://localhost:8080/usuario/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password, verificationPassword }),
                credentials: "include"
            });
            if (respond.ok) {
                await loginRequest(username, password);
                // Login automatico sin guardar respond
                navigate("/index");
            } else {
                const text = await respond.text();
                setPaso(1);
                setMessage(text);
            }
        } catch (error) {
            setMessage("Error de conexión");
        }
    };

    return (
        <div className="body-register">
            {paso == 1 && (
                <AuthBox titulo="Registrarse"
                botonDer={
                    <Button variant="default" color="oscuro" isDisabled={isDisabled} onClick={() => setPaso(paso + 1)}>Siguiente</Button>
                }
                linkExtra={
                    <Link to="/login">¿Ya tienes una cuenta? ¡Inicia sesión!</Link>
                }>
                    <Input type="text" value={username} name="username" onChange={e => setUsername(e.target.value)}>Email</Input>
                    <Input type="password" value={password} name="password" onChange={e => setPassword(e.target.value)}>Contraseña</Input>
                    <Input type="password" value={verificationPassword} name="verificationPassword" onChange={e => setVerificationPassword(e.target.value)}>Confirmar contraseña</Input>
                </AuthBox>
            )}

            {paso == 2 && (
                <AuthBox titulo="Ya casi..."
                botonDer={
                    <Button variant="default" color="oscuro" isDisabled={isDisabled} onClick={() => setPaso(paso + 1)}>Siguiente</Button>
                }
                botonIzq={
                    <Button variant="default" color="oscuro" onClick={() => setPaso(paso - 1)}>Atrás</Button>
                }>
                    <Input type="text" value={nickname} name="nickname" onChange={e => setNickname(e.target.value)}>Nombre de usuario</Input>
                    <Input type="text" value={fechaNacimiento} name="fechaNacimiento" onChange={e => setfechaNacimiento(e.target.value)}>Fecha de nacimiento</Input>
                </AuthBox>
            )}

            {paso == 3 && (
                <AuthBox titulo="Lo último..." onSubmit={handleRegister}
                botonDer={
                    <Button type='submit' variant="default" color="oscuro" isDisabled={isDisabled}>Aceptar</Button>
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
}