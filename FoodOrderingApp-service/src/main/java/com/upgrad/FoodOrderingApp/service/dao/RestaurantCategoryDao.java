package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantCategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RestaurantCategoryEntity> getAllRestaurantCategoryByRestaurantId(String categoryId)
    {
        try
        {
            return entityManager.createNamedQuery("getAllCategoryByRestaurantId",
                    RestaurantCategoryEntity.class).setParameter("categoryId", categoryId).getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

}
