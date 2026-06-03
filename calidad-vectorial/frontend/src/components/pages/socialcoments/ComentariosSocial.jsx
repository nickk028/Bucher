import { LibroCard } from "../../elements/book/LibroCard";
import { ComentarioSocial } from "../../elements/social/ComentarioSocial";
import { Button } from "../../elements/buttons/Button";
import { Input } from "../../elements/input/Input";

import cenicienta from "../../../assets/img/avatares/cenicienta.png";
import MencionarUsuarioPosteo from "../../../assets/img/posteo/mencionarUsuarioPosteo.svg?react";
import MencionarLibroPosteo from "../../../assets/img/posteo/mencionarLibroPosteo.svg?react";
import SubirImagenPosteo from "../../../assets/img/posteo/subirImagenPosteo.svg?react";
import ColorLetra from "../../../assets/img/posteo/colorLetra.svg?react";
import NegritaPosteo from "../../../assets/img/posteo/negritaPosteo.svg?react";
import SubrayarPosteo from "../../../assets/img/posteo/subrayarPosteo.svg?react";
import TacharPosteo from "../../../assets/img/posteo/tacharPosteo.svg?react"

import "./ComentariosSocial.css";

export const ComentariosSocial = () => {
    return(
        <main className="body-comsocial">
            <form className="body-comsocial__form">
                <div className="body-comsocial__form__content">
                    <div>
                        <img src={cenicienta} alt="Foto de perfil" />
                    </div>
                    <textarea className="body-comsocial__form__content__text" name="posteo" id="posteo" />
                    <div>
                        <LibroCard urlFoto="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVLofgGGZgYTRDxxH39fTr4mZfBg7nok7PZg&s" titulo="Sigue lloviendo" />
                    </div>
                </div>
                <div className="body-comsocial__form__options">
                    <div>
                        <div className="body-comsocial__form__options__buttons">
                        <MencionarUsuarioPosteo />
                        <MencionarLibroPosteo />
                        <SubirImagenPosteo />
                    </div>
                    <div className="body-comsocial__form__options__buttons">
                        <ColorLetra />
                        <NegritaPosteo />
                        <SubrayarPosteo />
                        <TacharPosteo />
                    </div>
                    <div className="body-comsocial__form__options__buttons">
                        <p>Contiene spoilers</p>
                        <input id="spoiler" name="spoiler" className="body-comsocial__form__options__buttons__checkbox" type="checkbox"></input>
                    </div>
                    </div>
                    <Button type="submit" variant="default" color="oscuro">Publicar</Button>
                </div>
            </form>
            <section>
                <ComentarioSocial
                    nickname="Usuario de ejemplo 1"
                    urlFoto="/assets/img/avatares/reinadecorazones.png"
                    tiempoPublicacion="3"
                >
                    Estuve semanas con bloqueo lector 😩, ningún libro me atrapaba. Pero arranqué Juliette y las canciones perdidas y me sacó de ese pozo.
                    <br />
                    Tip personal 👉 elegir algo juvenil, ágil y con personajes intensos... a mí siempre me rescata.
                </ComentarioSocial>
                <ComentarioSocial
                    nickname="Usuario de ejemplo 2"
                    urlFoto="/assets/img/avatares/dorothy.png"
                    tiempoPublicacion="3"
                >
                    Volví a leer El Principito ✨. Qué increíble cómo cada vez revela algo nuevo: hoy me recordó que lo esencial sigue estando en lo pequeño.
                </ComentarioSocial>
            </section>
        </main>
    );
};