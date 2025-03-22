package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao.CartDao;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartProduct;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CartService {

    public Map<Integer, CartProduct> data ;
    CartDao dao;
    ProductDAO productDAO;

    public CartService(){
        this.dao = new CartDao();
        this.productDAO = new ProductDAO();
        this.data = dao.getCartProducts();
    }

    public boolean add(Product p, int userId){
        if (data.containsKey(p.getId())){
            int qty = data.get(p.getId()).getQuantity()+p.getStock();
            update(p.getId(), qty);
            return true;
        }
        data.put(p.getId(), convert(p));

        // create cart
        int cartId = dao.createCart(userId);

        // add products to cart
        CartProduct cp = data.get(p.getId());
        dao.add(cartId, 1, cp.getQuantity(), p.getUnitPrice(), p.getProductDetailId());

        return true;
    }

    public boolean update(int id, int quantity){
        if(!data.containsKey(id)){
            return false;
        }
        if(quantity > 0) {
            CartProduct cp = data.get(id);
            Product p = productDAO.getById(id);
            cp.setQuantity(quantity);
            cp.setUnitPrice(cp.getQuantity()*p.getUnitPrice());
            return true;
        }
        return false;
    }

    public boolean remove(int id){
        return data.remove(id) != null;
    }

    public List<CartProduct> getList(){
        return new ArrayList<>(data.values());
    }

    public void updateQuantity(int productId, int newQuantity) {
        CartProduct cp = data.get(productId);
        if (cp != null) {
            cp.setQuantity(newQuantity);
        }
    }
    public int getTotalQuantity() {
        AtomicInteger totalQuantity = new AtomicInteger(0);
        data.forEach((k, v) -> totalQuantity.addAndGet(v.getQuantity()));
        return totalQuantity.get();
    }

    public Double getTotal() {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        data.values().forEach(cp -> total.updateAndGet(v -> v + cp.getQuantity() * cp.getUnitPrice()));
        return total.get();
    }

    public CartProduct convert(Product p){
        CartProduct result = new CartProduct();
        result.setId(p.getId());
        result.setName(p.getName());
        result.setQuantity(p.getStock());
        result.setUnitPrice(result.getQuantity() * p.getUnitPrice());
        result.setImages(p.getImages());
        result.setSizes(p.getSizes());
        result.setColors(p.getColors());
        return result;
    }
}
