package ar.com.educacionit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//1- indico que esta clas producto es una entidada
@Entity
@Table(name = "producto")
public class Producto {

	//pk
	@Id
	//autoincrental
	@GeneratedValue(strategy = GenerationType.IDENTITY) // TODO: ver si usamos UUID
	private Long id;
	
	@Column(name = "titulo", length = 50, nullable = false)
	private String titulo;
	
	@Column(name = "codigo", length = 6, nullable = false, unique = true)
	private String codigo;
	
	@Column(name = "precio", nullable = false)
	private Double precio;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_producto", referencedColumnName = "id")
	private TipoProducto tipoProducto;
	
	public Producto() {
		// necesitamos si o si esto!
	}

	//para cuando venga un dato desde la DB
	public Producto(Long id, String titulo, String codigo, Double precio,TipoProducto tipoProducto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.codigo = codigo;
		this.precio = precio;
		this.tipoProducto = tipoProducto;
	}

	//para enviar a la DB
	public Producto(String titulo, String codigo, Double precio) {
		super();
		this.titulo = titulo;
		this.codigo = codigo;
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", titulo=" + titulo + ", codigo=" + codigo + ", precio=" + precio + "]";
	}
	

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
}


/*
new Producto(1l, "titulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulotitulo", ....);
service.save(producto)

JPA
1- valida que los datos dentro del objeto 
 TITULO > 50
 > NO ARMA EL SQL 
*/