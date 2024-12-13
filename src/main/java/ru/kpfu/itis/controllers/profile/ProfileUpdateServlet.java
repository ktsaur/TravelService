package ru.kpfu.itis.controllers.profile;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entities.User;
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


@WebServlet("/profile/update")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50   // 50MB
)
public class ProfileUpdateServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private UserDao userDao;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    private static final String FILE_PREFIX = "/tmp";
    private static final int DIRECTORIES_COUNT = 10;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userDao = (UserDao) config.getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            req.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/WEB-INF/views/profile/profileUpdate.jsp").forward(req, resp);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
            int user_id = Integer.parseInt(req.getParameter("user_id"));
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String action = req.getParameter("action");

            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute("user");

            String uploadedUrl;

//            User user = new User(user_id, username, email, null);
//            boolean updated = false;
//            boolean updated = userDao.updateUserInfo(user);

            Part part = req.getPart("file");
            if (part != null && part.getSize() > 0) {
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                File file = new File(FILE_PREFIX + File.separator + fileName.hashCode() % DIRECTORIES_COUNT + File.separator + fileName);

                InputStream content = part.getInputStream();
                file.getParentFile().mkdirs();
                file.createNewFile();

                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[content.available()];
                    content.read(buffer);
                    outputStream.write(buffer);
                }

                Map uploadResult = cloudinary.uploader().upload(file, new HashMap<>());
//                String uploadedUrl = (String) uploadResult.get("url");
//                user.setUrl(uploadedUrl);
                uploadedUrl = (String) uploadResult.get("url");
//                try {
//                    user = new User(user_id, username, email, uploadedUrl);
//                    updated = userDao.updateUserInfo(user);
//                    LOG.info("фото загрузилось:" + updated);
//
//                } catch (DbException e) {
//                    req.setAttribute("message", "Ошибка при обновлении фото.");
//                }
            } else {
                uploadedUrl = currentUser.getUrl();
            }

            User user = new User(user_id, username, email, uploadedUrl);

            boolean updated = false;
            if ("updateInfo".equals(action) || part != null) {
                try {
                    updated = userDao.updateUserInfo(user);
                } catch (DbException e) {
                    throw new RuntimeException(e);
                }
            }

            if (updated) {
//                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                resp.sendRedirect(req.getContextPath() + "/profile");
            } else {
                req.setAttribute("message", "Не удалось обновить персональные данные");
                getServletContext().getRequestDispatcher("/WEB-INF/views/profile/profileUpdate.jsp").forward(req, resp);
            }

    }

}
