package ua.lviv.lgs.service.impl;

import ua.lviv.lgs.dao.BucketDao;
import ua.lviv.lgs.dao.impl.BucketDaoImpl;
import ua.lviv.lgs.domain.Bucket;
import ua.lviv.lgs.service.BucketService;

import java.sql.SQLException;
import java.util.List;

public class BucketServiceImpl implements BucketService {
    private BucketDao bucketDao;

    public BucketServiceImpl() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        bucketDao = new BucketDaoImpl();
    }

    @Override
    public Bucket create(Bucket bucket) throws SQLException, SQLException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket read(Integer id) throws SQLException {
        return bucketDao.read(id);
    }

    @Override
    public Bucket update(Bucket bucket) throws SQLException {
        return bucketDao.update(bucket);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        bucketDao.delete(id);
    }

    @Override
    public List<Bucket> readAll() throws SQLException {
        return bucketDao.readAll();
    }
}
