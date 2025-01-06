package ar.com.educacionit.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ar.com.educacionit.domain.TipoProducto;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.services.ProductoService;
import ar.com.educacionit.services.impl.ProductoServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class TipoProductoBean implements Serializable{

	private static final long serialVersionUID = -4361370965622954788L;

	private ProductoService ps = new ProductoServiceImpl();
	
	private List<TipoProducto> tipoProductos;
	
	@PostConstruct
	private void loadTipoProductos() {
		try {
			this.tipoProductos = ps.findTipoProductos();
		} catch (ServiceException e) {			
			e.printStackTrace();
			this.tipoProductos = new ArrayList<>();
		}
	}
	
	public TipoProducto[] getTipoProductos() {
		return this.tipoProductos.toArray(new TipoProducto[] {});
	}
}
