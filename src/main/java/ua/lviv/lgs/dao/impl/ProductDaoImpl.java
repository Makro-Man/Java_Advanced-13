package ua.lviv.lgs.dao.impl;

import ua.lviv.lgs.dao.ProductDao;
import ua.lviv.lgs.domain.Product;
import ua.lviv.lgs.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    static String READ_ALL = "select * from product";
    static String CREATE = "insert product(product_name, description, price) values (?,?,?)";
    static String READ_BY_ID = "select * from product where id=?";
    static String UPDATE_BY_ID = "update product set name_magazines=?,description=?,price=? where id=?";
    static String DELETE_BY_ID = "delete from product where id=?";

    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDaoImpl() {
        try {
            this.connection = ConnectionUtils.openConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Product create(Product product) throws SQLException {
        preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setDouble(3,product.getPrice());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        product.setId(resultSet.getInt(1));
        return product;
    }

    @Override
    public Product read(Integer id) throws SQLException {
        Product product;
        preparedStatement = connection.prepareStatement(READ_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        int productId = res.getInt("id");
        String name = res.getString("product_name");
        String description = res.getString("description");
        double price = res.getDouble("price");

        product = new Product(name, description, price);
        return product;
    }

    @Override
    public Product update(Product product) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
        preparedStatement.setString(1,product.getName());
        preparedStatement.setString(2,product.getDescription());
        preparedStatement.setDouble(3,product.getPrice());
        preparedStatement.executeUpdate();
        return product;
    }

    @Override
    public void delete(Integer id) throws SQLException {
        preparedStatement = connection.prepareStatement(DELETE_BY_ID);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
    public List<Product> readAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement(READ_ALL);
        ResultSet res = preparedStatement.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            String name = res.getString("product_name");
            String description = res.getString("description");
            double price = res.getDouble("price");
            list.add(new Product(id, name, description, price));
        }
        return list;
    }
}
