package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RestaurantService {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CustomerService customerService;

    public List<RestaurantEntity> getRestaurantByRating() {
        List<RestaurantEntity> restaurantList = restaurantDao.getAllRestaurantByRatings();
        return restaurantList;
    }

    public List<RestaurantEntity> getMatchingRestaurantByName(String restaurantName) throws RestaurantNotFoundException {
        if (restaurantName == null) {
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantList = restaurantDao.getMatchingRestaurantByName(restaurantName);
        return restaurantList;
    }

    public List<RestaurantEntity> getRestaurantById(String restaurantId) throws RestaurantNotFoundException {

        if (restaurantId == null) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }

        List<RestaurantEntity> restaurantList = restaurantDao.getRestaurantById(restaurantId);
        if (restaurantList == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return restaurantList;
    }

  /*  public List<RestaurantEntity> getRestaurantByCategoryId(String categoryId) throws  CategoryNotFoundException {
        if (categoryId == null) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        List<RestaurantEntity> restaurantList =categoryDao.getAllRestaurantCategoryByRestaurantId(categoryId);
        if(restaurantList != null)
        {
            return restaurantList;
        }
        else {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

    }*/

    public List<RestaurantCategoryEntity> getRestaurantByCategoryId(final String categoryUuidId) {
        return restaurantDao.getRestaurantByCategoryId(categoryUuidId);
    }

    public RestaurantEntity updateRestaurantRating(String restaurantId, Double rating, String authorization) throws RestaurantNotFoundException, InvalidRatingException, AuthorizationFailedException {

        customerService.getCustomer(authorization);
        if (restaurantId == null) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }

        if(!(rating>=Double.parseDouble("1")&& rating<=Double.parseDouble("5")))
        {
            throw new InvalidRatingException("IRE-001","Restaurant should be in the range of 1 to 5");
        }
        List<RestaurantEntity> restaurantList = restaurantDao.getRestaurantById(restaurantId);
        if (restaurantList == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        RestaurantEntity restaurantEntity=restaurantList.get(0);
        double currentRating= restaurantEntity.getCustomerRating();
        int noOfCustomer=restaurantEntity.getNoOfCustomerRated();
        double avgRatings= (currentRating*noOfCustomer + rating)/(noOfCustomer+1);
        System.out.println(avgRatings);
        restaurantEntity.setNoOfCustomerRated(noOfCustomer+1);
        restaurantEntity.setCustomerRating(avgRatings);
        RestaurantEntity updatedRestaurantEntity= restaurantDao.updateRating(restaurantEntity);
        return updatedRestaurantEntity;
    }

}