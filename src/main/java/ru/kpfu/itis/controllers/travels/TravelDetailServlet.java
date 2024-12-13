package ru.kpfu.itis.controllers.travels;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.util.CloudinaryUtil;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.validation.TraversableResolver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/travel/detail")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class TravelDetailServlet extends HttpServlet {

    private TravelDao travelDao;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    private static final String FILE_PREFIX = "/tmp";
    private static final int DIRECTORIES_COUNT = 10;

    @Override
    public void init() throws ServletException {
        super.init();
        this.travelDao = (TravelDao) getServletContext().getAttribute("travelDao");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            String travel_id = req.getParameter("travel_id");

            if (travel_id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No travel_id or route_id has been provided.");
                return;
            }
            Travel travel = travelDao.getTravelById(Integer.parseInt(travel_id));
            if (travel != null) {
                // Устанавливаем атрибуты запроса для JSP
                req.setAttribute("travel", travel);
               // req.setAttribute("route", route);

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);

            }
            //req.setAttribute("travel", travel);
            getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travel.jsp").forward(req, resp);

        } catch (SQLException | DbException e) {
            throw new RuntimeException(e);
        }


        /*
        try {
            // Получаем travel_id из параметров запроса
            String travelIdParam = req.getParameter("travel_id");

            // Получаем объект путешествия по travel_id
            Travel travel = travelDao.getTravelById(Integer.parseInt(travelIdParam));

            if (travel != null) {
                // Получаем объект маршрута по route_id, который связан с этим путешествием
                Route route = routeDao.getRouteById(travel.getRoute_id());

                // Устанавливаем атрибуты запроса для JSP
                req.setAttribute("travel", travel);
                req.setAttribute("route", route);
            }

            // Перенаправляем на страницу travel/detail.jsp
            getServletContext().getRequestDispatcher("/WEB-INF/views/travel/detail.jsp").forward(req, resp);

        } catch (DbException | NumberFormatException e) {
            // Обработка ошибок
            //e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load travel details.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */

/*        try {
            String travel_id = req.getParameter("travel_id");
            if (travel_id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id has been provided.");
            }
            Travel travel = travelDao.getTravelById(Integer.parseInt(travel_id));
            //мы обращаемся к BookDao и если он говорит, что книга не найдена, то даем 404 статусный код
            if (travel == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/notfound.jsp").forward(req, resp);
            }
            req.setAttribute("travel", travel);
            getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travel.jsp").forward(req, resp);
        } catch (DbException e) {
            throw new ServletException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int travel_id = Integer.parseInt(req.getParameter("travel_id"));

//        Part part = req.getPart("file");
//
//        if (part == null || part.getSubmittedFileName() == null || part.getSubmittedFileName().isEmpty()) {
//            req.setAttribute("message", "Фотография не была загружена. Предыдущее фото остается без изменений.");
//            getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
//            return;
//        }
//
//        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
//        File file = new File(FILE_PREFIX + File.separator + fileName.hashCode() % DIRECTORIES_COUNT + File.separator + fileName);
//
//        InputStream content = part.getInputStream();
//        file.getParentFile().mkdirs();
//        file.createNewFile();
//
//        FileOutputStream outputStream = new FileOutputStream(file);
//        byte[] buffer = new byte[content.available()];
//        content.read(buffer);
//        outputStream.write(buffer);
//        outputStream.close();
//
//        Map uploadResult = cloudinary.uploader().upload(file, new HashMap<>());
//        String uploadedUrl = (String) uploadResult.get("url");
//
//        try {
//            travelDao.updateTravelUrl(travel_id, uploadedUrl);
//            Travel travel = travelDao.getTravelById(travel_id);
//            travel.setTravel_url(uploadedUrl);
//        } catch (DbException e) {
//            req.setAttribute("message", "Ошибка при обновлении аватара.");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        // Проверяем, если действие - это удаление аккаунта
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
            String uploadedUrl = (String) uploadResult.get("url");

            try {
                travelDao.updateTravelUrl(travel_id, uploadedUrl);
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при обновлении фото.");
            }
        }

        if ("delete".equals(action)) {
            try {
                boolean isDeleted = travelDao.deleteTravel(travel_id);
                if (isDeleted) {
                    resp.sendRedirect(req.getContextPath() + "/travel/list");
                } else {
                    req.setAttribute("message", "Не удалось удалить аккаунт. Попробуйте позже.");
                    resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
                }
            } catch (DbException e) {
                req.setAttribute("message", "Ошибка при удалении путешествия. Попробуйте позже.");
                //getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            }
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
        }
    }
}
