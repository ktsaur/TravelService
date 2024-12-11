package ru.kpfu.itis.services;

import ru.kpfu.itis.dao.FavouritesDao;
import ru.kpfu.itis.util.DbException;
import ru.kpfu.itis.util.FavouriteActionResult;

import java.sql.SQLException;

public class FavouritesService {

    private FavouritesDao favouritesDao;

    public FavouritesService(FavouritesDao favouritesDao) {
        this.favouritesDao = favouritesDao;
    }

    public boolean isFavourite(int userId, int articleId) throws SQLException {
        return favouritesDao.isFavourite(userId, articleId);
    }

    public FavouriteActionResult actionWithFavourite(int user_id, int favourite_id) throws SQLException, DbException {
        boolean isFavourite = favouritesDao.isFavourite(user_id, favourite_id);
        boolean success;

        if (isFavourite) {
            success = favouritesDao.removeFavourite(user_id, favourite_id);
        } else {
            success = favouritesDao.addFavourite(user_id, favourite_id);
        }

        return new FavouriteActionResult(success, !isFavourite);
    }
}
