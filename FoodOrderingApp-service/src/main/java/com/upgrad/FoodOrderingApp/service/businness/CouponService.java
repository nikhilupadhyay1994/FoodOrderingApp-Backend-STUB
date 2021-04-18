package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CouponService {
    @Autowired
    CouponDao couponDao;

    @Autowired
    CustomerService customerService;

    @Transactional
    public CouponEntity getCouponByName(String couponName, final String authorization) throws AuthorizationFailedException, CouponNotFoundException {
        customerService.getCustomer(authorization);
        if(couponName == null || couponName.isEmpty()){
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }
        CouponEntity couponEntity=couponDao.getCouponByName(couponName);
        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
        return couponEntity;
    }

    @Transactional
    public CouponEntity getCouponByUuid(final String couponUuid) {
        return couponDao.getCouponByUuid(couponUuid);
    }
}
