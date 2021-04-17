package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_category")
@NamedQueries(
        {
                //NamedQuery(name = "categoryByUuid", query = "select a from CategoryEntity a order by a.customerRating desc"),
                 @NamedQuery(name = "getAllCategoryByRestaurantId", query = "select a from RestaurantCategoryEntity a where a.categoryEntity.uuid =:categoryId"),
        }
)
public class RestaurantCategoryEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JoinColumn(name = "category_id")
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private CategoryEntity  categoryEntity;

    @JoinColumn(name = "restaurant_id")
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private RestaurantEntity restaurantEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
