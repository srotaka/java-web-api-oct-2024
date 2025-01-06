import { memo } from "react";

function Data({json} = props) {//desestrucutacion

    const head = () => {
        return <thead>
                    <tr>
                        <th>ID</th>
                        <th>YEAR</th>
                        <th>COLOR</th>
                        <th>PANTONE_VALUE</th>
                        <th>NAME</th>
                    </tr>
                </thead>
    }
    const row = () => {
        /*jsx*/
        return  json.data.map(producto =>  
            <tr key={producto.id}>
                    <td>{producto.id}</td>
                    <td>{producto.year}</td>
                    <td>{producto.color}</td>
                    <td>{producto.pantone_value}</td>
                    <td>{producto.name}</td>
                </tr>
            );
    }

    return (
        <section>           
            <table>
                {head()}
                <tbody>
                    {row()}
                </tbody>
            </table>
        </section>
    );
};

export default memo(Data);