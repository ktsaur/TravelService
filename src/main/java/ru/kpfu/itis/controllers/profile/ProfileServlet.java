package ru.kpfu.itis.controllers.profile;

import com.cloudinary.Cloudinary;
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
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/profile")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class ProfileServlet extends HttpServlet {

    private UserService userService;
    private UserDao userDao;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    private static final String FILE_PREFIX = "/tmp";
    private static final int DIRECTORIES_COUNT = 10;

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

        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Part part = req.getPart("file");

        if (part == null || part.getSubmittedFileName() == null || part.getSubmittedFileName().isEmpty()) {
            req.setAttribute("message", "Фотография не была загружена. Предыдущее фото остается без изменений.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            return;
        }

        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        File file = new File(FILE_PREFIX + File.separator + fileName.hashCode() % DIRECTORIES_COUNT + File.separator + fileName);

        InputStream content = part.getInputStream();
        file.getParentFile().mkdirs();
        file.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[content.available()];
        content.read(buffer);
        outputStream.write(buffer);
        outputStream.close();

        Map uploadResult = cloudinary.uploader().upload(file, new HashMap<>());
        String uploadedUrl = (String) uploadResult.get("url");

        try {
            userDao.updateUserUrl(user.getId(), uploadedUrl);
            user.setUrl(uploadedUrl);
            session.setAttribute("user", user);
        } catch (DbException e) {
            req.setAttribute("message", "Ошибка при обновлении аватара.");
        }

        // Проверяем, если действие - это удаление аккаунта
        String action = req.getParameter("action");
        if ("delete".equals(action) && user != null) {
            try {

                userService.deleteUser(user, req, resp);
                userDao.deleteUser(user.getId());
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/signin");
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при удалении аккаунта. Попробуйте позже.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            }
        }

    }
}
