package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@NamedQueries(
        {
                @NamedQuery(name = "categoryByUuid", query = "select a from CategoryEntity a where a.uuid =:categoryUUid"),
                // @NamedQuery(name = "AllMatchRestaurantByName", query = "select a from RestaurantEntity a where a.uuid =:restaurantId"),
        }
)
public class CategoryEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    private String uuid;

    @Column(name="category_name")
    private String categoryName;


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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
