package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CouponDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponByName(String couponName) {
        try {
            return entityManager.createNamedQuery("couponByName", CouponEntity.class).setParameter("couponName", couponName)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public CouponEntity getCouponByUuid(String couponId) {
        try {
            return entityManager.createNamedQuery("couponByUuid", CouponEntity.class).setParameter("uuid", couponId)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }
}
