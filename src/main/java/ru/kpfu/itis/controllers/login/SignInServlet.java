package ru.kpfu.itis.controllers.login;

import ru.kpfu.itis.dao.UserDaoImpl;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.services.UserService;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    private UserDaoImpl userDao;
    private UserService userService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        //Метод init вызывается при инициализации сервлета. Он принимает объект ServletConfig,
        //который содержит конфигурационные данные для сервлета.
        super.init(config);
        userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
        userService = (UserService) getServletContext().getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/login/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username != null && password != null) {
            try {
                User user = userDao.getUsernameAndPassword(username, password); // аутентификация. мы проверили,
                // что такой пользолватель существует, что правильно соотносится логин и пароль
                int userId = userDao.getUserId(username);
                if (user == null) {
                    req.setAttribute("message", "Wrong pair username-password.");
                    getServletContext().getRequestDispatcher("/WEB-INF/views/login/signin.jsp").forward(req, resp);
                    return;
                } else {
                    userService.authUser(user, req, resp);
                    req.getSession().setAttribute("user_id", userId);
                    //resp.sendRedirect(getServletContext().getContextPath() + "/");
                }
            } catch (DbException e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/travel/list");
    }
}
