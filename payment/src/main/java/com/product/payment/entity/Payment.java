package com.product.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name="payment_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @Column(name="payment_id")
    private int paymentId;

    @Column(name="payment_amount")
    private double paymentAmount;

    @Column(name="order_id")
    // @ManyToOne(targetEntity = Order.class, foreignKey = @ForeignKey(name="fk__payment__order"))
    private int orderId;


    
}
