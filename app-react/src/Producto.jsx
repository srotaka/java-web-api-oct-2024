import { memo } from "react";

function Producto(props) {//desde afuera a un componente le paso Props

    const producto = props.producto; // tomo el atributo producto de las props

    console.log(producto);//muestro lo que viene por props

    return (
        <section>           
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>TITULO</th>
                        <th>CODIGO</th>
                        <th>PRECIO</th>
                        <th>TIPO</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{producto.id}</td>
                        <td>{producto.titulo}</td>
                        <td>{producto.codigo}</td>
                        <td>{producto.precio}</td>
                        <td>{producto.tipo.descripcion}</td>
                    </tr>
                </tbody>
            </table>
        </section>
    );
};

export default memo(Producto);