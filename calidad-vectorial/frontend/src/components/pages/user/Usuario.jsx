import PublicacionCard from "../../elements/publication/PublicacionCard";

import cenicienta from "../../../../public/assets/img/avatares/cenicienta.png";
import "./Usuario.css";

export const Usuario = () => {
    return (
        <main className="usuario">
            <section className="usuario__section">
                <div className="usuario__section__img">
                    <img src={cenicienta} alt="Foto del usuario" />
                </div>
                <div className="usuario__section__content">
                    <div className="usuario__section__content__datos">
                        <div className="usuario__section__content__datos__encabezado">
                            <h1>Laura lectora</h1>
                            <p>Lector / Escritor</p>
                        </div>
                        <ul className="usuario__section__content__datos__lista">
                            <li>
                                <p>12</p>
                                <p>Libros</p>
                            </li>
                            <li>
                                <p>5</p>
                                <p>Publicaciones</p>
                            </li>
                            <li>
                                <p>13</p>
                                <p>Posteos</p>
                            </li>
                        </ul>
                    </div>
                    <div className="usuario__section__content__descripcion">
                        <p>Descripción lorem</p>
                    </div>
                </div>
                <div className="usuario__section__puntos">
                    Puntos
                </div>
            </section>

            <section className="usuario__content">
                <ul className="usuario__content__clasificacion">
                    <li className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--selected`}>
                        Publicaciones de préstamos
                    </li>
                    <li className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--`}>
                        Posteos sociales
                    </li>
                    <li className={`usuario__content__clasificacion__item usuario__content__clasificacion__item--`}>
                        Biblioteca
                    </li>
                </ul>

                <section className="usuario__content__libros">
                    <PublicacionCard
                        urlFoto="https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/772d1279-adde-4a29-ba82-11071178969c/d_360_620/380957_portada_el-hobbit-edicion-revisada_j-r-r-tolkien_202306071037.webp"
                        titulo="El Hobbit"
                        usuarioNickname="Wendy's"
                        estadoPublicacion="disponible"
                        limiteDias="20"
                    />
                    <PublicacionCard
                        urlFoto="https://upload.wikimedia.org/wikipedia/en/6/6b/DaVinciCode.jpg"
                        titulo="El código Da Vinci"
                        usuarioNickname="Gimena"
                        estadoPublicacion="disponible"
                        limiteDias="20"
                    />
                    <PublicacionCard
                        urlFoto="https://upload.wikimedia.org/wikipedia/commons/1/1a/It_%281986%29_front_cover%2C_first_edition.jpg"
                        titulo="It"
                        usuarioNickname="Nickk028"
                        estadoPublicacion="disponible"
                        limiteDias="15"
                    />
                    <PublicacionCard
                        urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e8afc1c5-a0e5-4db5-afd5-6ad3ace888f3/9789500771870_fc43a029-ebe3-43d1-a6ae-b174e283ee70.jpg"
                        titulo="Mi nombre es Emilia del Valle"
                        usuarioNickname="Rooo07"
                        estadoPublicacion="disponible"
                        limiteDias="15"
                    />
                    <PublicacionCard
                        urlFoto="https://acdn-us.mitiendanube.com/stores/001/731/769/products/9789877255133-7ea7703ade2608762e16964226268090-640-0.jpg"
                        titulo="Cementerio de animales"
                        usuarioNickname="IgnPine"
                        estadoPublicacion="disponible"
                        limiteDias="12"
                    />
                </section>
            </section>
        </main>
    )
}