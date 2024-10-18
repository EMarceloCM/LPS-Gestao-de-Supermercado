package repository.interfaces;

import java.util.List;

public interface IRepository<T> {
    T find(int id);
    T find(T obj);
    List<T> findAll();
    void update(T obj);
    void save(T obj);
    boolean delete(int id);
    boolean delete(T obj);
}