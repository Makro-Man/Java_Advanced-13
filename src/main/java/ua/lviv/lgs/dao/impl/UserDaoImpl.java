package ua.lviv.lgs.dao.impl;

import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    static String READ_ALL = "select * from user";
    static String CREATE = "insert user(first_name, last_name, email, role) values (?,?,?,?)";
    static String READ_BY_ID = "select * from user where id=?";
    static String UPDATE_BY_ID = "update user set first_name=?,last_name=? where id=?";
    static String DELETE_BY_ID = "delete from user where id=?";

    public UserDaoImpl() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionUtils.openConnection();
    }

    private final Connection connection;
    private PreparedStatement preparedStatement;
    @Override
    public User create(User user) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User read(Integer id) throws SQLException {
        User user;
        preparedStatement = connection.prepareStatement(READ_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        String firstName = res.getString("first_name");
        String lastName = res.getString("las_name");
        String email = res.getString("email");
        String role = res.getString("role");

        user = new User(firstName, lastName, email, role);

        return user;
    }

    @Override
    public User update(User user) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
        preparedStatement.setString(1,user.getFirstName());
        preparedStatement.setString(2,user.getLastName());
        preparedStatement.executeUpdate();
        return user;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        preparedStatement = connection.prepareStatement(DELETE_BY_ID);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
    public List<User> readAll() throws SQLException {
        List<User> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement(READ_ALL);
        ResultSet res = preparedStatement.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            String firstName = res.getString("firs_name");
            String lastName = res.getString("last_name");
            String email = res.getString("email");
            String role = res.getString("role");
            list.add(new User(id,firstName, lastName, email, role));
        }
        return list;
    }
}
