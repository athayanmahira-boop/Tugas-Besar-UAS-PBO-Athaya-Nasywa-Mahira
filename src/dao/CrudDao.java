package dao;

import java.util.List;

// Interface CRUD generic (2b)
public interface CrudDao<T> {
    void insert(T data);
    List<T> getAll();
    void update(T data);
    void delete(int id);
}

