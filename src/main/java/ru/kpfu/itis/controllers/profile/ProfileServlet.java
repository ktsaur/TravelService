package ru.kpfu.itis.controllers.profile;

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

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserService userService;
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Проверяем, если действие - это удаление аккаунта
        String action = req.getParameter("action");
        if ("delete".equals(action) && user != null) {
            try {
                // Удаляем пользователя из базы данных
                userService.deleteUser(user, req, resp);
                userDao.deleteUser(user.getId());
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/signin");
//                } else {
//                    req.setAttribute("message", "Не удалось удалить аккаунт. Попробуйте позже.");
//                    getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
//                }
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при удалении аккаунта. Попробуйте позже.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            }
        }

    }
}
