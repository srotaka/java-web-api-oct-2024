package ar.com.educacionit.rest.validator;

import ar.com.educacionit.rest.Validable;

public class ValidadorManger {
	
	public static Validable getValidador(String clave) {
		
		//determinar que validador aplicar
		if("Crear_Producto".equals(clave)) {
			return new CrearProductoValidador();//esta es la imple del validador
		}
		
		return null;
	}
}
