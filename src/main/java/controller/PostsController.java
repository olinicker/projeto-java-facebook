package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.MySQLUserDAO;
import model.dao.PostDAO;
import model.dao.UserDAO;


@WebServlet(urlPatterns = {"/posts", "/post/save", "/post/update", "/post/delete", "/form_post"})
public class PostsController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		
		String action = req.getRequestURI();

		System.out.println(action);

		switch (action) {
		case "/facebook/posts": {
			
			loadPosts(req);

			RequestDispatcher rd = req.getRequestDispatcher("posts.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/facebook/post/save": {

			String postId = req.getParameter("post_id");
			if (postId != null && !postId.equals(""))
				try {
					updatePost(req);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ModelException e) {
					e.printStackTrace();
				}
			else
				try {
					insertPost(req);
				} catch (NumberFormatException | ModelException e) {
					e.printStackTrace();
				}

			resp.sendRedirect("/facebook/posts");			
			break;
		}
		case "/facebook/post/update": {

			loadPost(req);

			RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
		
			
			rd.forward(req, resp);
			break;
		} case "/facebook/post/delete": {
			
			deletePost(req);
			
			resp.sendRedirect("/facebook/posts");
			break;
		} case "/facebook/form_post" : {
			
			loadUsers(req);
			RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
			rd.forward(req, resp);
			break;
			
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	
	private void loadUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		List<User> users = null;
		try {
			users = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (users != null)
			req.setAttribute("usuarios", users);
	}

	private void deletePost(HttpServletRequest req) {
		String postIdString = req.getParameter("postId");
		int postId = Integer.parseInt(postIdString);
		
		Post post = new Post(postId);
		
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		
		try {
			dao.delete(post);
		} catch (ModelException e) {
			// log no servidor
			e.getCause().printStackTrace();
			e.printStackTrace();
		}
	}

	private void updatePost(HttpServletRequest req) throws NumberFormatException, ModelException {
		Post post = createPost(req);
		
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.update(post);
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
		}
	}

	private Post createPost(HttpServletRequest req) throws NumberFormatException, ModelException {
		String postId = req.getParameter("post_id");
		String postContent = req.getParameter("post_content");
		String postUserId = req.getParameter("post_userId");
		
		Post post;
		if (postId == null || postId.isBlank())
			post = new Post();
		else post = new Post(Integer.parseInt(postId));
		
		post.setContent(postContent);
		
		MySQLUserDAO findUser = new MySQLUserDAO();
		
		try {
			User postUser = findUser.findById(Integer.parseInt(postUserId));	
			post.setUser(postUser);
			
		}catch(Exception e) {
			
			System.out.println(e);
		}
		
		return post;
	}

	private void loadPost(HttpServletRequest req) {
		String postIdParameter = req.getParameter("postId");

		int postId = Integer.parseInt(postIdParameter);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			Post post = dao.findById(postId);

			if (post == null)
				throw new ModelException("Post não encontrado para alteração");
			
			req.setAttribute("post", post);
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
		}
	}

	private void insertPost(HttpServletRequest req) throws NumberFormatException, ModelException {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.save(post);
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
		}
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
		
		//System.out.println("Total de posts: " + posts.size());

	}
	

}
