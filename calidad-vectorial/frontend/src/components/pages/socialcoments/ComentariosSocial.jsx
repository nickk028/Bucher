import { useFetch } from '../../utils/FetchUtils';
import { LibroCard } from '../../elements/book/LibroCard';
import { ComentarioSocial } from '../../elements/social/ComentarioSocial';
import { Button } from '../../elements/buttons/Button';
import { Input } from '../../elements/input/Input';

import cenicienta from '../../../assets/img/avatares/cenicienta.png';
import MencionarUsuarioPosteo from '../../../assets/img/icons/posteo/mencionarUsuarioPosteo.svg?react';
import MencionarLibroPosteo from '../../../assets/img/icons/posteo/mencionarLibroPosteo.svg?react';
import SubirImagenPosteo from '../../../assets/img/icons/posteo/subirImagenPosteo.svg?react';
import ColorLetra from '../../../assets/img/icons/posteo/colorLetra.svg?react';
import NegritaPosteo from '../../../assets/img/icons/posteo/negritaPosteo.svg?react';
import SubrayarPosteo from '../../../assets/img/icons/posteo/subrayarPosteo.svg?react';
import TacharPosteo from '../../../assets/img/icons/posteo/tacharPosteo.svg?react';

import './ComentariosSocial.css';

export const ComentariosSocial = () => {
    const { data: dataUsuario } = useFetch('usuario/propio');
    const { data: dataPosteos, loading: loadingPosteos, error: errorPosteos } = useFetch('publicacionSocial');

    return (
        <main className="body-comsocial">
            <form className="body-comsocial__form">
                <div className="body-comsocial__form__content">
                    <div className='body-comsocial__form__content__img'>
                        <img src={`/assets/img/avatares/${dataUsuario?.avatar}`} alt="Foto de perfil" />
                    </div>
                    <textarea
                        className="body-comsocial__form__content__text"
                        name="posteo"
                        id="posteo"
                    />
                    <div>
                        <LibroCard
                            urlFoto="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVLofgGGZgYTRDxxH39fTr4mZfBg7nok7PZg&s"
                            titulo="Sigue lloviendo"
                            estrllas={true}
                        />
                    </div>
                </div>
                <div className="body-comsocial__form__options">
                    <div className="body-comsocial__form__options__">
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
                            <label className="body-comsocial__form__options__buttons__label">
                                <input
                                    id="spoiler"
                                    name="spoiler"
                                    className="body-comsocial__form__options__buttons__checkbox"
                                    type="checkbox"
                                />
                                <span className="body-comsocial__form__options__buttons__checkbox-custom" />
                            </label>
                        </div>
                    </div>
                    <Button type="submit" variant="default" color="oscuro">
                        Publicar
                    </Button>
                </div>
            </form>
            <section>
                {dataPosteos.content && (
                    dataPosteos.content.map((posteo) => (
                        <ComentarioSocial
                            key={posteo.id}
                            nickname={posteo.usuario?.nickname}
                            urlFoto={`/assets/img/avatares/${posteo.usuario?.avatar}`}
                            tiempoPublicacion={posteo.tiempoTranscurrido}
                        >
                            {posteo.textoPublicacion}
                    </ComentarioSocial>
                    ))
                )}
            </section>
        </main>
    );
};
