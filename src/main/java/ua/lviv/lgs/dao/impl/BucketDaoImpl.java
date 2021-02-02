package ua.lviv.lgs.dao.impl;

import ua.lviv.lgs.dao.BucketDao;
import ua.lviv.lgs.domain.Bucket;
import ua.lviv.lgs.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BucketDaoImpl implements BucketDao {
    static String READ_ALL = "select * from bucket";
    static String CREATE = "insert bucket(user_id,product_id, purchase_date) values (?,?,?)";
    static String READ_BY_ID = "select * from bucket where id=?";
    static String DELETE_BY_ID = "delete from bucket where id=?";

    private Connection connection;
    private PreparedStatement preparedStatement;

    public BucketDaoImpl () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionUtils.openConnection();
    }

    @Override
    public Bucket create(Bucket bucket) throws SQLException {
        preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, bucket.getUser_id());
        preparedStatement.setInt(2, bucket.getProduct_id());
        preparedStatement.setDate(3, (java.sql.Date) bucket.getPurchase_date());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        bucket.setId(resultSet.getInt(1));
        return bucket;
    }

    @Override
    public Bucket read(Integer id) throws SQLException {
        Bucket bucket;
        preparedStatement = connection.prepareStatement(READ_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        int bucketId = res.getInt("id");
        int userId = res.getInt("user_id");
        int productId = res.getInt("product_id");
        java.util.Date nowDate = res.getDate("purchase_date");

        bucket = new Bucket(bucketId, userId, productId, nowDate);

        return bucket;
    }

    @Override
    public Bucket update(Bucket bucket) {
        throw new IllegalStateException("There is not update for bucket");
    }

    @Override
    public void delete(Integer id) throws SQLException {

        preparedStatement = connection.prepareStatement(DELETE_BY_ID);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }
    public List<Bucket> readAll() throws SQLException {
        List<Bucket> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement(READ_ALL);
        ResultSet res = preparedStatement.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            int userId = res.getInt("user_id");
            int productId = res.getInt("product_id");
            java.util.Date nowDate = res.getDate("purchase_date");
            list.add(new Bucket(id, userId, productId, nowDate));
        }
        return list;
    }
}
