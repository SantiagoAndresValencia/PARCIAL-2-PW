<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar médico</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container mt-4" style="max-width: 500px;">
		<h2>Editar médico</h2>

		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/medicos/actualizar/${medico.id}" method="post">
			<label class="form-label">Nombre</label>
			<input type="text" name="nombre" value="${medico.nombre}" maxlength="50" class="form-control mb-3" required>

			<label class="form-label">Especialidad</label>
			<input type="text" name="especialidad" value="${medico.especialidad}" maxlength="50" class="form-control mb-3">

			<label class="form-label">Usuario para iniciar sesión</label>
			<input type="text" value="${medico.usuario}" class="form-control mb-3" readonly>

			<label class="form-label">Nueva contraseña</label>
			<input type="password" name="password" minlength="4" placeholder="Déjela vacía para no cambiarla" class="form-control mb-3">

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<button type="submit" class="btn btn-primary">Actualizar</button>
			<a href="${pageContext.request.contextPath}/medicos" class="btn btn-secondary">Cancelar</a>
		</form>
	</div>
</body>
</html>
