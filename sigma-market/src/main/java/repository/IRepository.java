package repository;

import java.util.List;

public interface IRepository<T> {
    T find(int id);
    List<T> findAll();
    void update(T obj);
    void save(T obj);
    boolean delete(int id);
}