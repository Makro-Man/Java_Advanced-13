package ua.lviv.lgs.dao.impl;

import org.apache.log4j.Logger;
import ua.lviv.lgs.dao.BucketDao;
import ua.lviv.lgs.domain.Bucket;
import ua.lviv.lgs.service.BucketService;
import ua.lviv.lgs.service.impl.BucketServiceImpl;
import ua.lviv.lgs.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BucketDaoImpl implements BucketDao {
    static String READ_ALL = "select * from bucket";
    static String CREATE = "insert bucket(user_id,product_id, purchase_date) values (?,?,?)";
    static String READ_BY_ID = "select * from bucket where id=?";
    static String DELETE_BY_ID = "delete from bucket where id=?";

    private static Logger LOGGER = Logger.getLogger(BucketDaoImpl.class);

    private Connection connection;
    private PreparedStatement preparedStatement;

    public BucketDaoImpl() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionUtils.openConnection();
    }

    @Override
    public Bucket create(Bucket bucket) {
        try {
            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bucket.getUser_id());
            preparedStatement.setInt(2, bucket.getProduct_id());
            preparedStatement.setDate(3, new Date(bucket.getPurchase_date().getTime()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            bucket.setId(resultSet.getInt(1));
            throw new SQLException();
        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return bucket;
    }

    @Override
    public Bucket read(Integer id) {
        Bucket bucket = null;
        try {
            preparedStatement = connection.prepareStatement(READ_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            int bucketId = res.getInt("id");
            int userId = res.getInt("user_id");
            int productId = res.getInt("product_id");
            java.util.Date nowDate = res.getDate("purchase_date");

            bucket = new Bucket(bucketId, userId, productId, nowDate);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return bucket;
    }

    @Override
    public Bucket update(Bucket bucket) {
        throw new IllegalStateException("There is not update for bucket");
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

    public List<Bucket> readAll()  {
        List<Bucket> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(READ_ALL);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int bucketId = result.getInt("id");
                Integer user_id = result.getInt("user_id");
                Integer product_id = result.getInt("product_id");
                Date purchase_date = result.getDate("purchase_date");

                list.add(new Bucket(bucketId, user_id, product_id, purchase_date));
            }
        } catch (SQLException e) {

            LOGGER.error(e);
        }
        return list;
    }
}
