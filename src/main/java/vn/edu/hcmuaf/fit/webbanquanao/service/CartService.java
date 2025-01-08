package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.CartDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.CartProduct;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CartService {

    Map<Integer, CartProduct> data ;
    CartDao dao;


    public CartService(){
        this.dao = new CartDao();
        this.data = dao.getCartProducts();
    }
    public boolean add(Product p){
        if (data.containsKey(p.getId())){
            update(p.getId(), data.get(p.getId()).getQuantity()+1);
            return true;
        }
        data.put(p.getId(), convert(p));
        return true;
    }

    public boolean update(int id, int quantity){
        if(!data.containsKey(id)){
            return false;
        }
        CartProduct cp = data.get(id);
        cp.setQuantity(quantity);
        return true;
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
        result.setUnitPrice(p.getUnitPrice());
        result.setImages(p.getImages());
        result.setQuantity(1);
        result.setSizes(p.getSizes());
        result.setColors(p.getColors());
        return result;
    }

}
