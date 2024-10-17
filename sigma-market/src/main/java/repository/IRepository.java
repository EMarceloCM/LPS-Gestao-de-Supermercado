package repository;

public interface IRepository<T> {
    void save(T obj);
    T find(int id);
    void update(T obj);
    void delete(int id);
}