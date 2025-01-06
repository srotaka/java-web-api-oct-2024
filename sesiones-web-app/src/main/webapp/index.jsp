<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Clase2 - Java WebApi</title>
</head>
<body>
	<!-- me da el nombre de contexto (dinamico) -->
	<form
		method="get" 
		action="<%=request.getContextPath()%>/api/producto">
		<input type="hidden" name="id" value="1"/>		
		<button>
			Consultar
		</button>
	</form>
</body>
</html>