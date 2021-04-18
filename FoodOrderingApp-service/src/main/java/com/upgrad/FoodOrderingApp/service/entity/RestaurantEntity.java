package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "restaurant")
@NamedQueries(
        {
                  @NamedQuery(name = "AllRestaurantByRatings", query = "select a from RestaurantEntity a order by a.customerRating desc"),
                // @NamedQuery(name = "AllMatchRestaurantByName", query = "select a from RestaurantEntity a where a.uuid =:restaurantId"),
        }
)

public class RestaurantEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid", length=16)
    @NotNull
    private String uuid;

    @Column(name="restaurant_name")
    @NotNull
    private String restaurantName;

    @Column(name="photo_url")
    @NotNull
    private String photoUrl;

    @Column(name="customer_rating")
    @NotNull
    private Double customerRating;

    @Column(name = "average_price_for_two")
    @NotNull private Integer avgPrice;

    @Column(name = "number_of_customers_rated")
    @NotNull
    private Integer noOfCustomerRated=0;

    @OneToOne
    @JoinColumn(name = "address_id")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private AddressEntity addressEntity;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName="id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName="id", nullable = false)
    )

    private List<CategoryEntity> category = new ArrayList<>();


    public List<CategoryEntity> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryEntity> category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Double customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getNoOfCustomerRated() {
        return noOfCustomerRated;
    }

    public void setNoOfCustomerRated(Integer noOfCustomerRated) {
        this.noOfCustomerRated = noOfCustomerRated;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

}
