<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<title>Cadastro Usuário</title>
</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-2"></div>

			<form action="${pageContext.request.contextPath}/user/create" method="GET" class="col-8">
				<h1>Cadastro de Usuário</h1>
				
				<!-- input para verificar se está alterando ou criando, pegando o id, se tem id faremos a comparação -->
				<input type="hidden" id="user_id" name="user_id" value="${usuario.getId()}"/>

				<div class="mb-3">
					<label for="user_name_id" class="form-label">Nome</label>
					<input type="text" id="user_name_id" name="user_name"
						class="form-control" value="${usuario.getName()}">
				</div>

				<div class="mb-3">
					<label class="form-label">Sexo</label>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="user_gender"
							value="M" id="user_gender_m" ${usuario.getGender().equals("M") ? "checked" : ""}>
						<label class="form-check-label" for="user_gender_m"> Masculino </label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="user_gender"
							value="F" id="user_gender_f" ${usuario.getGender().equals("F") ? "checked" : ""}>
						<label class="form-check-label" for="user_gender_f"> Feminino </label>
					</div>
				</div>

				<div class="mb-3">
					<label for="user_email_id" class="form-label">Email</label>
					<input type="email" id="user_email_id" name="user_email"
						class="form-control" value="${usuario.getEmail()}" >
				</div>
				
				<div class="mb-3">
					<label for="user_password_id" class="form-label">Password</label>
					<input type="password" id="user_password_id" name="user_password"
						class="form-control" value="${usuario.getPassword()}" >
				</div>

				<button type="submit" class="btn btn-primary">Enviar</button>
			</form>

			<div class="col-2"></div>
		</div>
	</div>

	<script src="js/bootstrap.bundle.min.js"></script>
</body>

</html>