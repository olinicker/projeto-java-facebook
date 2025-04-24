<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Facebook CRUD</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
  </head>
  <body>
	<div class="d-flex flex-column min-vh-100 bg-light">
		<!-- Navbar -->
	    <nav class="navbar navbar-dark bg-primary">
	      <div class="container">
	        <span class="navbar-brand mb-0 h1"><strong>Facebook</strong></span>
	        <a href="/facebook/logout" class="btn btn-light">
		    	<i class="bi bi-door-closed"></i> Logout
		  	</a>
	      </div>
	    </nav>
	
	    <!-- Conteúdo principal -->
	    <main class="container my-5">
    	<div class="text-center">
		        <h1 class="mb-4">Página Inicial</h1>
		
		        <c:if test="${not empty usuario_logado}">
		            <h2 class="mb-3">Bem-vindo, ${usuario_logado.name}!</h2>
		        </c:if>
		
		        <div class="d-flex justify-content-center flex-wrap gap-3">
		            <!-- Botões ou links futuros -->
		             <a href="/facebook/users" class="btn btn-outline-secondary btn-lg">Usuários</a>
		             <a href="/facebook/posts" class="btn btn-outline-secondary btn-lg">Posts</a> 
		        </div>
		    </div>
		</main>

	    
	
	
	    <!-- Rodapé -->
	    <footer class="bg-dark text-white text-center py-3 mt-auto">
	      <div class="container">
	        <p class="mb-0">© 2025 Facebook. Todos os direitos reservados.</p>
	      </div>
	    </footer>
	</div>
	
  </body>
</html>
