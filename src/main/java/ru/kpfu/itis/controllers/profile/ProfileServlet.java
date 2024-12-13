package ru.kpfu.itis.controllers.profile;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.services.UserService;
import ru.kpfu.itis.util.CloudinaryUtil;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
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
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            try {
                // Получаем обновленного пользователя из базы данных
                User updatedUser = userDao.getUserById(user.getId());
                session.setAttribute("user", updatedUser); // Обновляем данные в сессии
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при загрузке данных профиля.");
            }
        }

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
