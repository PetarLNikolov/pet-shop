package com.example.s13firstspring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Data

public class OrdersHaveProductsKey implements Serializable {

    @Column(name = "product_id")
    int productId;

    @Column(name = "order_id")
    int orderId;

    public OrdersHaveProductsKey() {
    }

    public OrdersHaveProductsKey(int productId, int orderId) {
        this.productId = productId;
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersHaveProductsKey that = (OrdersHaveProductsKey) o;
        return productId == that.productId && orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, orderId);
    }
}
