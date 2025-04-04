package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

//Rotas
@WebServlet(urlPatterns = {"", "/user/create", "/user/update"})
public class UsersController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String action = req.getRequestURI();
		System.out.println(action);

		switch (action) {
		case "/facebook/": {
			// Listagem dos usuários
			listUsers(req);

			// Redirecionar para a página de exibição (index)
			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/facebook/user/create" : {
			insertUser(req);

			// Redireciona para a listagem 
			resp.sendRedirect("/facebook");
			break;
		}
		
		case "/facebook/user/update" : {
			String userIdStr = req.getParameter("userId");
			int userId = Integer.parseInt(userIdStr);
			System.out.println(userId);
			
			UserDAO dao = DAOFactory.createDAO(UserDAO.class);
			
			User user = new User();
			
			try {
				user = dao.findById(userId);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			req.setAttribute("usuario", user);
			
			RequestDispatcher rd = req.getRequestDispatcher("/form_user.jsp");
			rd.forward(req, resp);
			
			break;
		}

		default:
			throw new IllegalArgumentException("URL não reconhecida: " + action);
		}
	}

	private void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		List<User> users = new ArrayList<User>();
		try {
			users = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}

		req.setAttribute("usuarios", users);
	}

	private void insertUser(HttpServletRequest req) {
		// Recuperando os parametros da requisição
		// Imputs (name) do HTML
		String userName = req.getParameter("user_name");
		String userGender = req.getParameter("user_gender");
		String userEmail = req.getParameter("user_email");

		// Cria e seta os valores do usuário
		User user = new User();
		user.setName(userName);
		user.setGender(userGender);
		user.setEmail(userEmail);

		// Salva o usuário no Banco
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		try {
			dao.save(user);
		} catch (ModelException e) {
			System.err.println("Erro ao salvar usuário");
			e.printStackTrace();
		}		
	}
}
