package ar.com.educacionit.client;

import ar.com.educacionit.repository.dtos.ProductoDTO;

public interface SoapClient {
	public ProductoDTO getById(Long id);
}
