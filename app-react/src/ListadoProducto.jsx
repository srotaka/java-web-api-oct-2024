import { memo } from "react";

function ListadoProducto(props) {//desde afuera a un componente le paso Props

    const productos = props.productos; // tomo el atributo producto de las props

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
                    {  productos.map(p => 
                            <tr key={p.id}>
                                <td>{p.id}</td>
                                <td>{p.titulo}</td>
                                <td>{p.codigo}</td>
                                <td>{p.precio}</td>
                                <td>{p.tipo.descripcion}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </section>
    );
};

export default memo(ListadoProducto);