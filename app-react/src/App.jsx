import { useEffect, useState } from 'react'

import './App.css'// hoja de estilos css
import ListadoProducto from './ListadoProducto';

function App() {

  /*estado */
  const [productos,setProductos] = useState([])
  const [username,setUsername] = useState('')
  const [password,setpassword] = useState('')
  
  useEffect(()=> {
    if(username && password && (username.length >= 4 && password.length >= 4) ) {
      //post
      fetch(`http://localhost:8080/app-rest-server/api/auth?username=${username}&password=${password}`,{
        method:'post',
      }).then(response => 
        //get
        fetch('http://localhost:8080/app-rest-server/api/producto',{
          headers: {
            'authorization': response.headers.get('Access-Token')
          }
        }) 
          .then(response => response.json()) 
          .then(data => setProductos(adapat(data)))    
      );    
    }
  },[username,password]);

  const adapat = (response) => {
    const convertidos = response.map(r => { 
      return {
          id: r.id,
          titulo: r.titulo,
          codigo: r.codigo,
          precio: r.precio,
          tipo: {
            id: r.tipoProducto.id,
            descripcion: r.tipoProducto.descripcion
          }
        }
      }
    );
    return convertidos;
  }
  /*
  useEffect(()=> {
    console.log('useEffect cada vez que cambia una dep');
  },[contador]);
  */

  /*jsx*/
  return (
    /*fragment */
    /*asociar un cli evento a un elemento*/

    <>
    <div>
      <label>Username:</label>
      <input value={username} onChange={(e) => setUsername(e.target.value)}></input>
    </div>
    <div>
      <label>Password:</label>
      <input value={password} onChange={(e) => setpassword(e.target.value)}></input>
    </div>

      {productos.length > 0 &&
        <ListadoProducto productos={productos}/>
      }
     
    </>
  )
}

export default App
