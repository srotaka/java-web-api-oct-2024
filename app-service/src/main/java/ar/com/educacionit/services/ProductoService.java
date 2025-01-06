package ar.com.educacionit.services;
import java.util.List;

import ar.com.educacionit.domain.TipoProducto;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.repository.dtos.ProductoDTO;

public interface ProductoService {

	public ProductoDTO getById(Long id);

	public List<ProductoDTO> findAll();

	public ProductoDTO create(ProductoDTO request);

	public ProductoDTO getByCodigo(String codigo);

	public void eliminar(Long id);

	public void actualizar(ProductoDTO productoActualizar);
	
	public List<TipoProducto> findTipoProductos() throws ServiceException;

}
