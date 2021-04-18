package com.upgrad.FoodOrderingApp.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ItemQuantity {

    @JsonProperty("item_id")
    private UUID itemId = null;

    @JsonProperty("quantity")
    private Integer quantity = null;

    @JsonProperty("price")
    private Integer price = null;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
