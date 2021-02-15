package ua.lviv.lgs.shared;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDao <T>{
    T create(T t);
    T read(Integer id);
    T update(T t);
    void delete(Integer id);
    List<T> readAll();
}
