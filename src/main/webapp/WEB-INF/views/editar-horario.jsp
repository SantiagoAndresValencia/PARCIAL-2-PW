<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cambiar horario</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container mt-4" style="max-width: 500px;">
		<h2>Cambiar horario de la cita</h2>
		<p>Motivo: <b>${consulta.motivo}</b><br>Médico: ${consulta.medico.nombre} - Consultorio ${consulta.consultorio}</p>

		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/consultas/horario/${consulta.id}" method="post">
			<label class="form-label">Hora de inicio</label>
			<input type="time" name="horaInicio" value="${consulta.horaInicio}" class="form-control mb-3" required>

			<label class="form-label">Hora de finalización</label>
			<input type="time" name="horaFin" value="${consulta.horaFin}" class="form-control mb-3" required>

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<button type="submit" class="btn btn-primary">Actualizar horario</button>
			<a href="${pageContext.request.contextPath}/consultas" class="btn btn-secondary">Cancelar</a>
		</form>
	</div>
</body>
</html>
