package ru.kpfu.itis.controllers.login;

import ru.kpfu.itis.dao.UserDaoImpl;
import ru.kpfu.itis.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signout")
public class SignOutServlet extends HttpServlet {

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
        //UserService.signOut(req);
        userService.signout(req);
        resp.sendRedirect(getServletContext().getContextPath() + "/main");
        //resp.sendRedirect(getServletContext().getContextPath() + "/");
    }
}
