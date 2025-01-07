package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.CartProduct;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class CartDao {

    JDBIConnector conn;
    String query;

    public CartDao() {
        conn = new JDBIConnector();
    }


    public Map<Integer, CartProduct> getCartProducts(){
        Map<Integer, CartProduct> cps = new HashMap<>();
        query = "SELECT " +
                "    p.id AS id," +
                "    p.productName AS name," +
                "    p.unitPrice AS unitPrice," +
                "    pd.image AS img," +
                "    cd.quantity AS quantity " +
                "FROM " +
                "    products p " +
                "JOIN " +
                "    product_details pd ON p.id = pd.productId " +
                "JOIN " +
                "    cartdetail cd ON p.id = cd.productId " +
                "JOIN " +
                "    cart c ON cd.cartId = c.id " +
                "WHERE " +
                "    c.id = ?";



        return conn.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(query)) {
                ps.setInt(1, 1);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CartProduct cp = new CartProduct();
                        cp.setId(rs.getInt("id"));
                        cp.setName(rs.getString("name"));
                        cp.setUnitPrice(rs.getDouble("unitPrice"));
                        cp.setImg(rs.getString("img"));
                        cp.setQuantity(rs.getInt("quantity"));
                        cps.put(cp.getId(), cp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cps;
        });

    }
}
