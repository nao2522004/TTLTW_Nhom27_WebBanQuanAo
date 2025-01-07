package vn.edu.hcmuaf.fit.webbanquanao.dao;

public interface CRUIDDao {
    boolean create(Object obj);
    boolean update(Object obj, String userName);
    boolean delete(String userName);
}
