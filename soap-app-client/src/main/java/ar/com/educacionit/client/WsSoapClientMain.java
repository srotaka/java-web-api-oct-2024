package ar.com.educacionit.client;

import com.example.soap.client.ProductoWsSoapService;
import com.example.soap.client.ProductoeWsSoapServiceImplService;
import com.example.soap.client.WsProductoDTO;

public class WsSoapClientMain {
	public static void main(String[] args) {
		//instanciamos el cliente
		ProductoeWsSoapServiceImplService _service = new ProductoeWsSoapServiceImplService();
		//accedemos al port
		ProductoWsSoapService service = _service.getProductoeWsSoapServiceImplPort();
		//invocamos uno de los sercicios getProdctoBy
		WsProductoDTO dto = service.getProdctoById(5L);
		
		System.out.println(dto.getTitulo());
		System.out.println(dto.getId());
		System.out.println(dto.getPrecio());
	}
}
