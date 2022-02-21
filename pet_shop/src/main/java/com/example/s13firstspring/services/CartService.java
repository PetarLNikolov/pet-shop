package com.example.s13firstspring.services;

import com.example.s13firstspring.models.entities.Product;
import org.springframework.stereotype.Service;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@ManagedBean
@Service
public class CartService implements SessionScoped{
        private Product cart;
        HashMap<Integer,Product> hm=new HashMap<Integer,Product>();

        public Product getCart(){
            return cart;
        }
        public void setCart(Product cart){
            this.cart=cart;
        }
        // Добавить корзину
        public void addCart(Product product){
            if(hm.containsKey(product.getId())){
                String name= product.getName();
                hm.put(product.getId(),product);
            }else{
                hm.put(product.getId(), product);
            }
        }
        // Delete some product
        public void deleteBook(Product product){
            if(hm.containsKey(product.getId())){
                String name=product.getName();
                hm.remove(product.getId());
            }
            else {
                System.out.println("Have no this product in cart");
            }

        }
        // clean cart
        public  void clearCart(){
            hm.clear();
        }
        // get all price
        public double getTotalPrice(){
            double totalPrice=0.0;

            ArrayList<Product> a=new ArrayList<Product>();
            Iterator it=hm.keySet().iterator();
            while(it.hasNext()){
                int b = (Integer) it.next ();
                Product product = hm.get (b);// TODO with count
                totalPrice+=product.getPrice();
            }
            return totalPrice;
        }
        // View all products in cart
        public ArrayList<Product> printCart(){
            ArrayList<Product> al=new ArrayList<Product>();

            Iterator it=hm.keySet().iterator();
            while(it.hasNext()){
                int id = (Integer) it.next ();
                Product product = hm.get (id);
                al.add(product);
            }
            return al;
        }
        public HashMap<Integer, Product> getHm() {
            return hm;
        }
        public void setHm(HashMap<Integer, Product> hm) {
            this.hm = hm;
        }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
