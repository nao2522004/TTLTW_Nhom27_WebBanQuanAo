package vn.edu.hcmuaf.fit.webbanquanao.dao.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.sql.SQLException;
import java.util.List;

public class JDBIConnector {
    public static Jdbi jdbi;

    public static void connect() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://" + Properties.HOST + ":" + Properties.PORT + "/" + Properties.DBNAME);
        ds.setUser(Properties.USERNAME);
        ds.setPassword(Properties.PASSWORD);
        try {
            ds.setAutoReconnect(true);
            ds.setUseCompression(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(ds);
    }

    public static Jdbi getJdbi() {
        if(jdbi == null) connect();
        return jdbi;
    }

//    public static void main(String[] args) {
//        List<Product> list = JDBIConnector.getJdbi().withHandle(handle -> {
//           return handle.createQuery("select * from products").mapToBean(Product.class).list();
//        });
//        System.out.println(list);
//    }
}
