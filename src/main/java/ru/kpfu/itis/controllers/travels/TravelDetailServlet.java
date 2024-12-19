package ru.kpfu.itis.controllers.travels;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.dao.TravelDaoImpl;
import ru.kpfu.itis.entities.Travel;
import ru.kpfu.itis.util.CloudinaryUtil;
import ru.kpfu.itis.util.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

    private TravelDaoImpl travelDao;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    private static final String FILE_PREFIX = "/tmp";
    private static final int DIRECTORIES_COUNT = 10;

    @Override
    public void init() throws ServletException {
        super.init();
        this.travelDao = (TravelDaoImpl) getServletContext().getAttribute("travelDao");

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
                req.setAttribute("travel", travel);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                getServletContext().getRequestDispatcher("/WEB-INF/views/errors/not_found.jsp").forward(req, resp);

            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/travels/travel.jsp").forward(req, resp);

        } catch (SQLException | DbException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int travel_id = Integer.parseInt(req.getParameter("travel_id"));

        try {
            if ("delete".equals(action)) {
                boolean isDeleted = travelDao.deleteTravel(travel_id);
                if (isDeleted) {
                    resp.sendRedirect(req.getContextPath() + "/travel/list");
                } else {
                    req.setAttribute("message", "Не удалось удалить путешествие. Попробуйте позже.");
                    resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
                }
            }
            if ("upload".equals(action)) {
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

                    travelDao.updateTravelUrl(travel_id, uploadedUrl);
                    resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
                } else {
                    req.setAttribute("message", "Не выбран файл для загрузки.");
                    req.getRequestDispatcher("/WEB-INF/views/travels/travel.jsp").forward(req, resp);
                }
                return;
            }
            if ("updateStatus".equals(action)) {
                boolean isOver = Boolean.parseBoolean(req.getParameter("isOver"));
                travelDao.updateTravelStatus(travel_id, isOver);
                resp.sendRedirect(req.getContextPath() + "/travel/detail?travel_id=" + travel_id);
                return;
            }
        } catch (DbException e) {
            req.setAttribute("message", "Произошла ошибка при обработке действия.");
            req.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(req, resp);
        }

    }
}
