package ua.lviv.lgs.service;

import ua.lviv.lgs.domain.Product;
import ua.lviv.lgs.shared.AbstractDao;

import java.util.Map;

public interface ProductService extends AbstractDao<Product> {
    public Map<Integer, Product> readAllMap();
}
