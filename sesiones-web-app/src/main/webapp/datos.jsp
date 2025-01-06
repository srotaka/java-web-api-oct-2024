<%@page import="ar.com.educacionit.repository.dtos.ProductoDTO"%>

<html>
	<head>
	</head>
	<body>
		<%
			//obtiene o baja el dato de sesion (el objetio session existe de manera implicita en las jsp)
			//debo castear de Object mi Objeto: ProductoDTO
			ProductoDTO producto = (ProductoDTO)session.getAttribute("PRODUCTO");			
		%>
		<div>
			<label>ID:</label> <%=producto.getId()%>
			<label>Precio:</label> <%=producto.getPrecio()%> 
			<label>T&iacute;tulo:</label> <%=producto.getTitulo()%> 
		</div>
	</body>
</html>