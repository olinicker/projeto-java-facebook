package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

// Rotas
@WebServlet(urlPatterns = {""})
public class UsersController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	//requisição -> todas as informações do navegador, fica no objeto req
	//resposta -> devolver para o navegador informações
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		System.out.println(action);

		switch(action) {
			case "/facebook/": {
				//Listagem dos usuários
				listUsers(req);
				//resp.sendRedirect("index.jsp");
				break;
			}
			default:
				throw new IllegalArgumentException("URL desconhecida" + action);
		}
			
	}

	private void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		
		List<User> users = new ArrayList<User>();
		
		try {
			users = dao.listAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//
		req.setAttribute("usuarios", users);
		
		
	}
}
