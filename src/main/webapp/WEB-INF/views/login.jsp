<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar sesión</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container mt-5" style="max-width: 400px;">
		<div class="card shadow">
			<div class="card-body">
				<h3 class="text-center">Clínica USCO</h3>
				<p class="text-center text-muted">Consultas médicas</p>

				<c:if test="${param.error != null}">
					<div class="alert alert-danger">Usuario o contraseña incorrectos</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">Sesión cerrada correctamente</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/login" method="post">
					<label class="form-label">Usuario</label>
					<input type="text" name="username" class="form-control mb-3" required>
					<label class="form-label">Contraseña</label>
					<input type="password" name="password" class="form-control mb-3" required>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<button type="submit" class="btn btn-primary w-100">Ingresar</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
