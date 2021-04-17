package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CategoryEntity getCategoryById(String categoryUUid)
    {
        try
        {
            return entityManager.createNamedQuery("categoryByUuid",
                    CategoryEntity.class).setParameter("categoryUUid",categoryUUid).getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

}
