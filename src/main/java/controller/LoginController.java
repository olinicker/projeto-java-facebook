package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String userLogin = req.getParameter("user_login");
		String userPW = req.getParameter("user_pw");

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		User user = null;

		try {
			user = dao.findByEmail(userLogin);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		if (user != null && PasswordEncryptor.checkPassword(userPW, user.getPassword())) {
			
			req.getSession().setAttribute("usuario_logado", user);
			
			
			loadPosts(req);
			
			
			//resp.sendRedirect("/facebook/");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		} else
			resp.sendRedirect("/facebook/login.jsp?erro=true");
	}
	
	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		List<Post> posts = null;
		try {
			posts = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (posts != null)
			req.setAttribute("posts", posts);
		
		System.out.println("Total de posts: " + posts.size());

	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		/* o att false indica que não é para criar uma sessão caso ela não exista
		HttpSession session = req.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}else {
			resp.sendRedirect("/facebook/login.jsp");
		} */
		
		  String path = req.getServletPath();

		    if ("/logout".equals(path)) {
		        HttpSession session = req.getSession(false);
		        if (session != null) {
		            session.invalidate();
		        }
		        resp.sendRedirect("/facebook/login.jsp");
		    } else {
		        // Qualquer outro GET (como /login), redireciona para o login
		        resp.sendRedirect("/facebook/login.jsp");
		    }
		
	}
}
