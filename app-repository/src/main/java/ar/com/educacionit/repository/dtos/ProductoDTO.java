package ar.com.educacionit.repository.dtos;

import ar.com.educacionit.domain.TipoProducto;

public class ProductoDTO {

	private Long id;
	private String titulo;
	private String codigo;
	private Double precio;
	private TipoProducto tipoProducto;
	
	public ProductoDTO() {
		
	}
	
	//mutable|inmutable?
	public ProductoDTO(
			Long id,
			String titulo,
			Double precio			
			) {
		
		setId(id);
		this.titulo = titulo;//¿hay control?
		this.precio = precio;
	}
	
	public ProductoDTO(Long id, String titulo, String codigo, Double precio, TipoProducto tipoProducto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.codigo = codigo;
		this.precio = precio;
		this.tipoProducto = tipoProducto;
	}

	public void setId(Long id) {
		//guards
		if(id == null || id < 0) {
			throw new IllegalArgumentException("Id no puede ser nulo ni < 0");//runtime
		}
		//al final todo ok 
		
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getTitulo() {
		return titulo;
	}

	public Double getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", titulo:" + titulo + ", precio:" + precio + "}";
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	//completar lo que falta
	//alt+shit+s
	
	
}
