package ua.lviv.lgs.service;

import java.util.List;

public interface AbstractService<T> {
    T create(T t);

    T read(Integer id);

    T update(T t);

    void delete(Integer id);

    List<T> readAll();
}
