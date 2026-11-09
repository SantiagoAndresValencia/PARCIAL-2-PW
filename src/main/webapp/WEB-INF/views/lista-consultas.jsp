<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consultas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-dark bg-info px-3">
		<div>
			<span class="navbar-brand">Clínica USCO</span>
			<a href="${pageContext.request.contextPath}/consultas" class="btn btn-sm btn-light me-1">Consultas</a>
			<sec:authorize access="hasAuthority('ADMINISTRADOR')">
				<a href="${pageContext.request.contextPath}/medicos" class="btn btn-sm btn-light">Médicos</a>
			</sec:authorize>
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
		<sec:authorize access="hasAuthority('ADMINISTRADOR')">
			<h2>Todas las consultas</h2>
			<a href="${pageContext.request.contextPath}/consultas/nueva" class="btn btn-success mb-3">Nueva consulta</a>
		</sec:authorize>
		<sec:authorize access="hasAuthority('MEDICO')">
			<h2>Mis consultas asignadas</h2>
		</sec:authorize>
		<sec:authorize access="hasAuthority('PACIENTE')">
			<h2>Mis citas</h2>
		</sec:authorize>

		<c:if test="${empty consultas}">
			<div class="alert alert-info">No hay consultas para mostrar</div>
		</c:if>

		<c:if test="${not empty consultas}">
			<table class="table table-bordered table-hover">
				<thead class="table-light">
					<tr>
						<th>ID</th>
						<th>Paciente</th>
						<th>Motivo</th>
						<th>Consultorio</th>
						<th>Horario</th>
						<th>Médico</th>
						<sec:authorize access="hasAnyAuthority('ADMINISTRADOR', 'PACIENTE')">
							<th>Acciones</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${consultas}">
						<tr>
							<td>${c.id}</td>
							<td>${c.nombrePaciente}</td>
							<td>${c.motivo}</td>
							<td>${c.consultorio}</td>
							<td>${c.horaInicio} - ${c.horaFin}</td>
							<td>${c.medico.nombre}</td>
							<sec:authorize access="hasAuthority('ADMINISTRADOR')">
								<td>
									<a href="${pageContext.request.contextPath}/consultas/editar/${c.id}" class="btn btn-sm btn-warning">Editar</a>
									<a href="${pageContext.request.contextPath}/consultas/eliminar/${c.id}" class="btn btn-sm btn-danger"
										onclick="return confirm('¿Seguro que desea eliminar la consulta?')">Eliminar</a>
								</td>
							</sec:authorize>
							<sec:authorize access="hasAuthority('PACIENTE')">
								<td>
									<a href="${pageContext.request.contextPath}/consultas/horario/${c.id}" class="btn btn-sm btn-warning">Cambiar horario</a>
								</td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>
