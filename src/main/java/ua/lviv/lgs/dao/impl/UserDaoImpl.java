package ua.lviv.lgs.dao.impl;

import org.apache.log4j.Logger;
import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    static String READ_ALL = "select * from user";
    static String CREATE = "insert user(first_name, last_name, email, role, password) values (?,?,?,?,?)";
    static String READ_BY_ID = "select * from user where id=?";
    static String READ_BY_EMAIL = "select * from user where email=?";
    static String UPDATE_BY_ID = "update user set email=?,first_name=?,last_name=?,role=?,password=? where id=?";
    static String DELETE_BY_ID = "delete from user where id=?";

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionUtils.openConnection();
    }

    private final Connection connection;
    private PreparedStatement preparedStatement;
    @Override
    public User create(User user)  {
        try {
            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e){
            LOGGER.error(e);
        }

        return user;
    }

    @Override
    public User read(Integer id)  {
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(READ_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            String firstName = res.getString("first_name");
            String lastName = res.getString("las_name");
            String email = res.getString("email");
            String role = res.getString("role");
            String password = res.getString("password");
            user = new User(firstName, lastName, email, role, password);
        }catch (SQLException e){
            LOGGER.error(e);
        }

        return user;
    }

    @Override
    public User update(User user)  {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getFirstName());
            preparedStatement.setString(3,user.getLastName());
            preparedStatement.setString(4,user.getRole());
            preparedStatement.setString(5,user.getPassword());
            preparedStatement.setInt(6,user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            user.setId(rs.getInt(1));
        }catch (SQLException e){
            LOGGER.error(e);
        }

        return user;
    }

    @Override
    public void delete(Integer id)  {
        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            LOGGER.error(e);
        }
    }
    public List<User> readAll()  {
        List<User> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(READ_ALL);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String firstName = res.getString("first_name");
                String lastName = res.getString("last_name");
                String email = res.getString("email");
                String role = res.getString("role");
                String password = res.getString("password");
                list.add(new User(firstName, lastName, email, role, password));
            }
        }catch (SQLException e){
            LOGGER.error(e);
        }

        return list;
    }
    @Override
    public User getUserFromEmail(String email) {
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(READ_BY_EMAIL);

            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            Integer userId = result.getInt("id");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String role = result.getString("role");
            String password = result.getString("password");

            user = new User(userId, email, firstName, lastName, role, password);

        } catch (SQLException e) {

            LOGGER.error(e);
        }
        return user;
    }
}
