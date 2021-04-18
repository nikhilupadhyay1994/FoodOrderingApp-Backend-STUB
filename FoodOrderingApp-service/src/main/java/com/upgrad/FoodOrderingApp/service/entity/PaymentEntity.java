package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment")
@NamedQueries(
        {
                @NamedQuery(name = "allPayments", query = "select p from PaymentEntity p "),
                @NamedQuery(name = "paymentById", query = "select p from PaymentEntity p where p.id=:id"),
                @NamedQuery(name = "paymentByUuid", query = "select p from PaymentEntity p where p.uuid=:uuid")
        }
)
public class PaymentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    @NotNull
    private String uuid;

    @Column(name="payment_name")
    @NotNull
    private String paymentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
}
