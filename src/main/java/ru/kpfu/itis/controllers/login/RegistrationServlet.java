package ru.kpfu.itis.controllers.login;

import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.services.UserService;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserDao userDao;
    //private UserService userService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        //Метод init вызывается при инициализации сервлета. Он принимает объект ServletConfig,
        //который содержит конфигурационные данные для сервлета.
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        //userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/login/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if(username == null || password == null || email == null) {
            req.setAttribute("message", "Все поля должны быть заполнены");
            getServletContext().getRequestDispatcher("/WEB-INF/views/login/registration.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userDao.getUsernameAndPassword(username, password);
            if (user != null) {
                req.setAttribute("message", "Пользователь с таким именем уже существует.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/login/registration.jsp").forward(req, resp);
            } else {
                User newUser = new User(username, password, email);
                userDao.addUser(newUser);

                req.setAttribute("message", "Регистрация прошла успешно");
                resp.sendRedirect(req.getContextPath() + "/signin");
            }
        } catch (DbException e) {
            req.setAttribute("message", "Произошла ошибка при регистрации. Попробуйте еще раз.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/login/registration.jsp").forward(req, resp);
        }
    }

}
