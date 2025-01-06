package ar.com.educacionit.rest.validator;

import java.util.HashMap;
import java.util.Map;

import ar.com.educacionit.repository.dtos.ProductoDTO;
import ar.com.educacionit.rest.Validable;

public class CrearProductoValidador implements Validable {
	
	@Override
	public Map<String, String> validate(Object context) {
		
		//debo castear el objeto con el que voy a trabajar
		var request = (ProductoDTO)context;
		
		var errores =  new HashMap<String, String>();
		
		//validaciones!
		if(request.getPrecio() == null) {
			errores.put("precio", "debe completar el precio");
		}
		//validaciones!
		if(request.getTitulo() == null) {
			errores.put("precio", "debe completar el precio");
		}
		
		return errores;
	}

}
