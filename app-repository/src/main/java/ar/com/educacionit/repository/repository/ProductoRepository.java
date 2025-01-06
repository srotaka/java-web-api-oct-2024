package ar.com.educacionit.repository.repository;

import java.util.List;

import ar.com.educacionit.domain.TipoProducto;
import ar.com.educacionit.exceptions.GenericException;
import ar.com.educacionit.repository.dtos.ProductoDTO;

public interface ProductoRepository {
	
	//CREATE
	public ProductoDTO save(ProductoDTO entity);
	
	//READ	
	public ProductoDTO getById(Long id);
	
	//UPDATE	
	public void update(ProductoDTO entity);
	
	//DELETE
	public void delete(Long id);

	public List<ProductoDTO> findAll();

	public ProductoDTO getByCodigo(String codigo);

	public List<TipoProducto> findTipoProductos() throws GenericException;
}
