package ua.lviv.lgs.dao.impl;

import org.apache.log4j.Logger;
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

    private static Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDaoImpl()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        connection = ConnectionUtils.openConnection();
    }

    @Override
    public Product create(Product product)  {
        try {
            preparedStatement = connection.prepareStatement(CREATE, java.sql.Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            product.setId(rs.getInt(1));

        } catch (SQLException e) {

            LOGGER.error(e);
        }

        return product;
    }

    @Override
    public Product read(Integer id)  {
        Product product = null;
        try {
            preparedStatement = connection.prepareStatement(READ_BY_ID);

            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            Integer productId = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");
            Double price = result.getDouble("price");

            product = new Product(productId, name, description, price);

        } catch (SQLException e) {

            LOGGER.error(e);
        }

        return product;
    }

    @Override
    public Product update(Product product)  {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            LOGGER.error(e);
        }
        return product;

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

    public List<Product> readAll()  {
        List<Product> productRecords = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(READ_ALL);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Integer productId = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Double price = result.getDouble("price");

                productRecords.add(new Product(productId, name, description, price));
            }
        } catch (SQLException e) {

            LOGGER.error(e);
        }

        return productRecords;
    }
}
