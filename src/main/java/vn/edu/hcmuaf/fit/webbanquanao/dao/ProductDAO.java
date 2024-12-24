package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.dao.db.DbConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // Hien thi san pham
    public List<Product> getListProducts() {
        List<Product> list = new ArrayList<Product>();
        String query = "select * from products";

        try {
            Connection conn = DbConn.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt("proid"),
                        rs.getInt("type_id"),
                        rs.getInt("category_id"),
                        rs.getInt("supplier_id"),
                        rs.getString("productName"),
                        rs.getString("size"),
                        rs.getInt("stock"),
                        rs.getString("images"),
                        rs.getString("colors"),
                        rs.getDate("releaseDate"),
                        rs.getInt("unitSold"),
                        rs.getDouble("unitPrice")));
            }
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return list;
    }
}
