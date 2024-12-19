package ru.kpfu.itis.controllers.profile;

import ru.kpfu.itis.dao.UserDaoImpl;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile/updatePassword")
public class ChangePasswordServlet extends HttpServlet {

    private UserDaoImpl userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile/changePassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");


        User user = (User) req.getSession().getAttribute("user");

        if (user != null && oldPassword != null && !oldPassword.isEmpty()) {
            if (user != null && user.getPassword().equals(oldPassword) ) {
                if (newPassword.equals(confirmPassword)) {
                    user.setPassword(newPassword);
                    boolean success;
                    try {
                        success = userDao.updatePasswordInDatabase(user);
                    } catch (DbException e) {
                        throw new RuntimeException(e);
                    }

                    if (success) {
                        req.setAttribute("message", "Пароль успешно изменен.");
                    } else {
                        req.setAttribute("error", "Произошла ошибка при обновлении пароля.");
                    }
                } else {
                    req.setAttribute("error", "Новые пароли не совпадают.");
                }
            } else {
                req.setAttribute("error", "Неверный старый пароль.");
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/profile/changePassword.jsp").forward(req, resp);
    }
}
