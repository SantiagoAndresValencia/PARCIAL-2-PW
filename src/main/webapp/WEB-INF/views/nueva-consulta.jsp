<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nueva consulta</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container mt-4" style="max-width: 600px;">
		<h2>Registrar consulta</h2>

		<c:if test="${error != null}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/consultas/guardar" method="post">
			<label class="form-label">Nombre del paciente</label>
			<input type="text" name="nombrePaciente" value="${consulta.nombrePaciente}" maxlength="40" class="form-control mb-3" required>

			<label class="form-label">Cuenta del paciente</label>
			<select name="usuarioPaciente" class="form-select mb-3" required>
				<option value="">Seleccione la cuenta del paciente</option>
				<c:forEach var="p" items="${pacientes}">
					<option value="${p.username}" ${consulta.usuarioPaciente == p.username ? 'selected' : ''}>${p.nombre} (${p.username})</option>
				</c:forEach>
			</select>

			<label class="form-label">Motivo de la consulta</label>
			<textarea name="motivo" maxlength="100" class="form-control mb-3" required>${consulta.motivo}</textarea>

			<label class="form-label">Número de consultorio</label>
			<input type="number" name="consultorio" value="${consulta.consultorio}" min="1" class="form-control mb-3" required>

			<div class="row">
				<div class="col">
					<label class="form-label">Hora de inicio</label>
					<input type="time" name="horaInicio" value="${consulta.horaInicio}" class="form-control mb-3" required>
				</div>
				<div class="col">
					<label class="form-label">Hora de finalización</label>
					<input type="time" name="horaFin" value="${consulta.horaFin}" class="form-control mb-3" required>
				</div>
			</div>

			<label class="form-label">Médico</label>
			<select name="medico.id" class="form-select mb-3" required>
				<option value="">Seleccione un médico</option>
				<c:forEach var="m" items="${medicos}">
					<option value="${m.id}" ${consulta.medico.id == m.id ? 'selected' : ''}>${m.nombre} - ${m.especialidad}</option>
				</c:forEach>
			</select>

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<button type="submit" class="btn btn-primary">Guardar</button>
			<a href="${pageContext.request.contextPath}/consultas" class="btn btn-secondary">Cancelar</a>
		</form>
	</div>
</body>
</html>
