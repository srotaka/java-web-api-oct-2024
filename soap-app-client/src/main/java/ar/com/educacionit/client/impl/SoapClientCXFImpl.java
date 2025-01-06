package ar.com.educacionit.client.impl;

import com.example.soap.client.ProductoWsSoapService;
import com.example.soap.client.ProductoeWsSoapServiceImplService;
import com.example.soap.client.WsProductoDTO;

import ar.com.educacionit.client.SoapClient;
import ar.com.educacionit.repository.dtos.ProductoDTO;

public class SoapClientCXFImpl implements SoapClient {

	@Override
	public ProductoDTO getById(Long id) {
		System.out.println("SoapClientCXFImpl");
		
		//instanciamos el cliente
		ProductoeWsSoapServiceImplService _service = new ProductoeWsSoapServiceImplService();
		
		//accedemos al port
		ProductoWsSoapService service = _service.getProductoeWsSoapServiceImplPort();
		
		//invocamos uno de los sercicios getProdctoBy
		WsProductoDTO wsDto = service.getProdctoById(5L);
		
		//retornamos 
		return new ProductoDTO(wsDto.getId(), wsDto.getTitulo(), wsDto.getPrecio());
	}

}
