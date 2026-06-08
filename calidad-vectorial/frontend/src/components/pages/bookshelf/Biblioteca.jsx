import {LibroCard} from "../../elements/book/LibroCard";
import "./Biblioteca.css";

export const Biblioteca = () => {
    return (
        <main className="biblioteca">
            <header className="biblioteca__encabezado">
                <h1>Mi biblioteca</h1>
                <p>LLeva registro de tus lecturas y libros</p>
            </header>

            <section className="biblioteca__contenedor">
                <section className="biblioteca__contenedor__aside">
                    <section className="biblioteca__contenedor__aside__clasificacion">
                        <div className="biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--todos">
                            Todos <br /> 12
                        </div>
                        <div className="biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--leidos">
                            Leídos <br /> 4
                        </div>
                        <div className="biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--leyendo">
                            Leyendo <br /> 2
                        </div>
                        <div className="biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--quiero-leer">
                            Quiero leer <br />3
                        </div>
                        <div className="biblioteca__contenedor__aside__clasificacion__card biblioteca__contenedor__aside__clasificacion__card--abandonados">
                            Abandonados <br /> 2
                        </div>
                    </section>

                    <section className="biblioteca__contenedor__aside__ordenar">
                        <h2>Ordenar por:</h2>
                        <ul className="biblioteca__contenedor__aside__ordenar__opciones">
                            <li className="biblioteca__contenedor__aside__ordenar__opciones__opcion">
                                Progreso
                            </li>
                            <li className="biblioteca__contenedor__aside__ordenar__opciones__opcion">
                                Autor
                            </li>
                            <li className="biblioteca__contenedor__aside__ordenar__opciones__opcion">
                                Clasificación
                            </li>
                            <li className="biblioteca__contenedor__aside__ordenar__opciones__opcion">
                                Título
                            </li>
                        </ul>
                    </section>
                </section>

                <section className="biblioteca__contenedor__libros">
                    <LibroCard titulo="Harry Potter y la piedra filosofal" urlFoto="https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg" clasificacion="quiero-leer" estrllas={false}></LibroCard>
                    <LibroCard titulo="Valeria en el espejo" urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/f4142c2b-6d82-4fcf-909b-4b84f87b9fdd/9789877392487_02d9255d-7e10-4f6e-8e41-680891222916.jpg" clasificacion="abandonados" estrllas={false}></LibroCard>
                    <LibroCard titulo="Mi nombre es Emilia del Valle" urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e8afc1c5-a0e5-4db5-afd5-6ad3ace888f3/9789500771870_fc43a029-ebe3-43d1-a6ae-b174e283ee70.jpg" clasificacion="leyendo" estrllas={false}></LibroCard>
                    <LibroCard titulo="El Retorno del Rey" urlFoto="https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/fbacf17d-96c3-45bf-a12b-8e0bb129ea37/d_360_620/400210_portada_el-senor-de-los-anillos-n-0303-el-retorno-del-rey-edicion-revisada_j-r-r-tolkien_202403121720.webp" clasificacion="leyendo" estrllas={false}></LibroCard>
                    <LibroCard titulo="It" urlFoto="https://upload.wikimedia.org/wikipedia/commons/1/1a/It_%281986%29_front_cover%2C_first_edition.jpg" clasificacion="quiero-leer" estrllas={false}></LibroCard>
                    <LibroCard titulo="El Hobbit" urlFoto="https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/772d1279-adde-4a29-ba82-11071178969c/d_360_620/380957_portada_el-hobbit-edicion-revisada_j-r-r-tolkien_202306071037.webp" clasificacion="leidos" estrllas={false}></LibroCard>
                    <LibroCard titulo="La cúpula" urlFoto="https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/f6e22475-224c-4db9-a5bb-fafd4932cd86/9789877257014_33eaf902-84af-4212-b715-686942bca64a.jpg" clasificacion="quiero-leer" estrllas={false}></LibroCard>
                    <LibroCard titulo="Testigo de cargo" urlFoto="https://images.cdn1.buscalibre.com/fit-in/360x360/5d/32/5d323f46674a3f55d9a581b5ac774979.jpg" clasificacion="abandonados" estrllas={false}></LibroCard>
                    <LibroCard titulo="El código Da Vinci" urlFoto="https://upload.wikimedia.org/wikipedia/en/6/6b/DaVinciCode.jpg" clasificacion="leidos" estrllas={false}></LibroCard>
                    <LibroCard titulo="Cementerio de animales" urlFoto="https://acdn-us.mitiendanube.com/stores/001/731/769/products/9789877255133-7ea7703ade2608762e16964226268090-640-0.jpg" clasificacion="quiero-leer" estrllas={false}></LibroCard>
                    <LibroCard titulo="El pasillo de la muerte" urlFoto="https://0.academia-photos.com/attachment_thumbnails/64970363/mini_magick20201118-14474-1vl1u7.png?1605708442" clasificacion="leidos" estrllas={false}></LibroCard>
                    <LibroCard titulo="Heartless" urlFoto="https://images.cdn3.buscalibre.com/fit-in/360x360/c2/3c/c23c70613dc1a56084d04b3ce04cc890.jpg" clasificacion="leyendo" estrllas={false}></LibroCard>
                </section>
            </section>
        </main>
    )
}
