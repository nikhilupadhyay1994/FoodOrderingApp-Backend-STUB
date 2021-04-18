package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RestaurantEntity> getAllRestaurantByRatings()
    {
        try
        {
            return entityManager.createNamedQuery("AllRestaurantByRatings",
                    RestaurantEntity.class).getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

    public List<RestaurantEntity> getRestaurantById(String restaurantId)
    {
        try
        {
            System.out.println("inside dao"+ restaurantId);
            Query query= entityManager.createQuery("select a from RestaurantEntity a where a.uuid = :restaurantId");
            query.setParameter("restaurantId", restaurantId);
             return  query.getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

    public List<RestaurantCategoryEntity> getRestaurantByCategoryId(final String categoryUuidId) {
        try {
            return entityManager.createNamedQuery("restaurantsByCategoryId", RestaurantCategoryEntity.class).setParameter("id",categoryUuidId).getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }


    public List<RestaurantEntity>  getMatchingRestaurantByName(String restaurantName)
    {
        try
        {
            restaurantName="%"+restaurantName+"%";
            System.out.println("inside dao");
            Query query= entityManager.createQuery("select a from RestaurantEntity a where a.restaurantName like :restaurantName");
            query.setParameter("restaurantName", restaurantName);
            return query.getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }
    @Transactional
    public RestaurantEntity updateRating(RestaurantEntity restaurantEntity)
    {
        try
        {
           return entityManager.merge(restaurantEntity);

        }catch (NoResultException nre)
        {
            return null;
        }

    }
}
