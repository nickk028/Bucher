import "./Index.css";
import { ComentarioSocial } from "../../elements/social/ComentarioSocial";
import PublicacionCard from "../../elements/publication/PublicacionCard";
import { LibroCard } from "../../elements/book/LibroCard";
import { Button } from "../../elements/buttons/Button";

export const Index = () => {
    return (
        <main className="index">
            <section className="index__section">
                <section className="index__section__lectura">
                    <span className="index__text">Lectura actual</span>
                    <article className="index__section__lectura__container">
                        <div className="index__section__lectura__img">
                            <img src="https://upload.wikimedia.org/wikipedia/commons/1/1a/It_%281986%29_front_cover%2C_first_edition.jpg" alt="Portada del libro" />
                        </div>
                        <div className="index__section__lectura__container__data">
                            <div>
                                <h1>It (Eso)</h1>
                            <p className="index__section__lectura__author">Stephen King</p>
                            </div>
                            <div className="index__section__lectura__progress">
                                <span className="index__section__lectura__progress__label">45% leído</span>
                                <div className="index__section__lectura__progress__bar">
                                    <div className="index__section__lectura__progress__fill" style={{ width: "45%" }} />
                                </div>
                                <span className="index__section__lectura__progress__page">Página actual: 98</span>
                            </div>
                            <div className="index__section__lectura__action">
                                <Button variant="default" color="oscuro">Actualizar progreso</Button>
                            </div>
                        </div>
                    </article>
                </section>
                <section>
                    <ComentarioSocial
                        nickname="Gimena"
                        urlFoto="/assets/img/avatares/reinadecorazones.png"
                        tiempoPublicacion="3"
                    >
                        Estuve semanas con bloqueo lector 😩, ningún libro me atrapaba. Pero arranqué Juliette y las canciones perdidas y me sacó de ese pozo.
                        <br />
                        Tip personal 👉 elegir algo juvenil, ágil y con personajes intensos... a mí siempre me rescata.
                    </ComentarioSocial>
                    <ComentarioSocial
                        nickname="Wendy's"
                        urlFoto="/assets/img/avatares/dorothy.png"
                        tiempoPublicacion="5"
                    >
                        Volví a leer El Principito ✨. Qué increíble cómo cada vez revela algo nuevo: hoy me recordó que lo esencial sigue estando en lo pequeño.
                    </ComentarioSocial>
                </section>
            </section>
            <aside className="index__aside">
                <section>
                    <span className="index__text">Préstamos que te pueden interesar</span>
                    <div className="index__aside__container">
                        <PublicacionCard
                            urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e8afc1c5-a0e5-4db5-afd5-6ad3ace888f3/9789500771870_fc43a029-ebe3-43d1-a6ae-b174e283ee70.jpg"
                            titulo="Mi nombre es Emilia del Valle"
                            usuarioNickname="Claudio"
                            estadoPublicacion="Disponible"
                            limiteDias="14"
                        />
                        <PublicacionCard
                            urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e8afc1c5-a0e5-4db5-afd5-6ad3ace888f3/9789500771870_fc43a029-ebe3-43d1-a6ae-b174e283ee70.jpg"
                            titulo="Mi nombre es Emilia del Valle"
                            usuarioNickname="Claudio"
                            estadoPublicacion="Disponible"
                            limiteDias="14"
                        />
                        <PublicacionCard
                            urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e8afc1c5-a0e5-4db5-afd5-6ad3ace888f3/9789500771870_fc43a029-ebe3-43d1-a6ae-b174e283ee70.jpg"
                            titulo="Mi nombre es Emilia del Valle"
                            usuarioNickname="Claudio"
                            estadoPublicacion="Disponible"
                            limiteDias="14"
                        />
                    </div>
                </section>
                <section>
                    <span className="index__text">Préstamos que te pueden interesar</span>
                    <div className="index__aside__container">
                        <LibroCard titulo="El código Da Vinci" autor="Isabel Allende" urlFoto="https://upload.wikimedia.org/wikipedia/en/6/6b/DaVinciCode.jpg"></LibroCard>
                        <LibroCard titulo="El pasillo de la muerte" autor="Stephen King" urlFoto="https://0.academia-photos.com/attachment_thumbnails/64970363/mini_magick20201118-14474-1vl1u7.png?1605708442"></LibroCard>
                        <LibroCard titulo="Yo antes de ti" autor="Jojo Moyes" urlFoto="https://images.cdn3.buscalibre.com/fit-in/360x360/d0/ac/d0ac4eadaa99b866d2e308b6500feaed.jpg" estrllas={false}></LibroCard>
                    </div>
                    </section>
            </aside>
        </main>
    );
}