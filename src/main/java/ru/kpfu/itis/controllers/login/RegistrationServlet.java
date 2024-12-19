package ru.kpfu.itis.controllers.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.UserDaoImpl;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.DbException;
import ru.kpfu.itis.util.PasswordUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServlet.class);
    private UserDaoImpl userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
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
            String encryptedPassword = PasswordUtil.encrypt(password);

            User user = userDao.getUsernameAndPassword(username, encryptedPassword);
            if (user != null) {
                req.setAttribute("message", "Пользователь с таким именем уже существует.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/login/registration.jsp").forward(req, resp);
            } else {
                User newUser = new User(username, encryptedPassword, email);
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
