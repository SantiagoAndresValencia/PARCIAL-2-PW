<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Médicos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-dark bg-info px-3">
		<div>
			<span class="navbar-brand">Clínica USCO</span>
			<a href="${pageContext.request.contextPath}/consultas" class="btn btn-sm btn-light me-1">Consultas</a>
			<a href="${pageContext.request.contextPath}/medicos" class="btn btn-sm btn-light">Médicos</a>
		</div>
		<div class="d-flex align-items-center text-white">
			<span class="me-3">
				<sec:authentication property="name" /> - <sec:authentication property="authorities" />
			</span>
			<a href="${pageContext.request.contextPath}/swagger-ui.html" class="btn btn-sm btn-light me-2">API Swagger</a>
			<form action="${pageContext.request.contextPath}/logout" method="post" class="m-0">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<button type="submit" class="btn btn-sm btn-outline-light">Cerrar sesión</button>
			</form>
		</div>
	</nav>

	<div class="container mt-4">
		<h2>Médicos</h2>

		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<a href="${pageContext.request.contextPath}/medicos/nuevo" class="btn btn-success mb-3">Nuevo médico</a>

		<table class="table table-bordered table-hover">
			<thead class="table-light">
				<tr>
					<th>ID</th>
					<th>Nombre</th>
					<th>Especialidad</th>
					<th>Usuario</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="m" items="${medicos}">
					<tr>
						<td>${m.id}</td>
						<td>${m.nombre}</td>
						<td>${m.especialidad}</td>
						<td>${m.usuario}</td>
						<td>
							<a href="${pageContext.request.contextPath}/medicos/editar/${m.id}" class="btn btn-sm btn-warning">Editar</a>
							<a href="${pageContext.request.contextPath}/medicos/eliminar/${m.id}" class="btn btn-sm btn-danger"
								onclick="return confirm('¿Seguro que desea eliminar al médico?')">Eliminar</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
