package ru.kpfu.itis.controllers.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.UserDaoImpl;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.services.UserService;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService;
    private UserDaoImpl userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        User user = (User) session.getAttribute("user");
//
//        if (user != null) {
//            try {
//                // Получаем обновленного пользователя из базы данных
//                User updatedUser = userDao.getUserById(user.getId());
//                session.setAttribute("user", updatedUser); // Обновляем данные в сессии
//            } catch (DbException e) {
//                req.setAttribute("message", "Ошибка при загрузке данных профиля.");
//            }
//        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            try {
                User updatedUser = userDao.getUserById(user.getId());
                session.setAttribute("user", updatedUser);
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при загрузке данных профиля.");
            }
        }

        // Определение текущей вкладки
        String tab = req.getParameter("tab");
        if (tab == null || (!tab.equals("personal") && !tab.equals("account"))) {
            tab = "personal"; // По умолчанию открывается вкладка "Персональная информация"
        }
        req.setAttribute("tab", tab);

        getServletContext().getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");
        if ("delete".equals(action) && user != null) {
            try {
                LOG.info("перед ввыполнением удаления");
                userDao.deleteUser(user.getUser_id());
                userService.deleteUser(user, req, resp);

                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/main");
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при удалении аккаунта. Попробуйте позже.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(req, resp);
            }
        }
    }
}
