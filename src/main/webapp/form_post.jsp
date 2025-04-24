<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<title>Cadastrar novo Post</title>
</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-2"></div>
			

			<form action="/facebook/post/save" method="GET" class="col-8">
			
			
				<c:if test="${empty post.id}">
				    <h1 class="mt-4">Cadastrar novo Post</h1>
				</c:if>
				
				<c:if test="${not empty post.id}">
				    <h1 class="mt-4">Editar Post</h1>
				</c:if>
			
				

				<input type="hidden" id="post_id" name="post_id"
					value="${post.getId()}" required>

				<div class="mb-3">
					<label for="post_content_id" class="form-label">Conteúdo do Post:</label> 
					<input
						type="text" id="post_content_id" name="post_content"
						class="form-control" value="${post.getContent()}" required>
				</div>
				
				
				<c:if test="${empty post.id}">
				<label for="post_userId">Usuário</label>
				    <label for="post_userId">Usuário</label>
				    <select name="post_userId" id="post_userId" class="form-select" required>
				        <option value="" disabled selected>Selecione um usuário</option>
				        <c:forEach var="usuario" items="${usuarios}">
				            <option value="${usuario.getId()}">${usuario.getName()}</option>
				        </c:forEach>
				    </select>
				</c:if>

				
				
				<button type="submit" class="mt-2 btn btn-primary">Enviar</button>
			</form>

			<div class="col-2"></div>
		</div>
	</div>

	<script src="js/bootstrap.bundle.min.js"></script>
</body>

</html>