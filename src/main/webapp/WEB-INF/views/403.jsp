<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acceso restringido</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container mt-5" style="max-width: 550px;">
		<div class="card border-danger shadow">
			<div class="card-body text-center">
				<h1 class="text-danger">Acceso restringido</h1>
				<p class="mt-3">
					Hola <b><sec:authentication property="name" /></b>, tu rol
					<b><sec:authentication property="authorities" /></b> no tiene permisos para realizar esta acción.
				</p>
				<p class="text-muted">Si crees que es un error, comunícate con el administrador.</p>
				<a href="${pageContext.request.contextPath}/consultas" class="btn btn-primary">Volver a las consultas</a>
			</div>
		</div>
	</div>
</body>
</html>
