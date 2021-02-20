package ua.lviv.lgs.service.impl;

import org.apache.log4j.Logger;
import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.dao.impl.UserDaoImpl;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);
    private static UserService userServiceImpl;
    private UserDao userDao;

    private UserServiceImpl() {

        try {
            userDao = new UserDaoImpl();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {

            LOGGER.error(e);
        }

    }

    public static UserService getUserService() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl();
        }
        return userServiceImpl;
    }
    @Override
    public User getUserFromEmail(String email) {

        return userDao.getUserFromEmail(email);
    }


    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User read(Integer id)  {
        return userDao.read(id);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public List<User> readAll()  {
        return userDao.readAll();
    }

}
