package com.example.s13firstspring.modelsTests.entities;


import javax.persistence.*;

@Entity
@Table(name="orders_have_products")

public class OrdersHaveProducts {
    @EmbeddedId
    OrdersHaveProductsKey id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name="product_id")
    Product product;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name="order_id")
    Order order;

    @Column
    int units;



    public OrdersHaveProducts(OrdersHaveProductsKey id, Product product, Order order, int units) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.units = units;
    }

    public OrdersHaveProducts(OrdersHaveProductsKey id, Product product, Order order) {
        this.id = id;
        this.product = product;
        this.order = order;
    }

    public OrdersHaveProducts() {

    }


    public OrdersHaveProductsKey getId() {
        return id;
    }

    public void setId(OrdersHaveProductsKey id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
