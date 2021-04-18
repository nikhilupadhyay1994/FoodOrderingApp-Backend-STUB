package com.upgrad.FoodOrderingApp.api.controller;
import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <RestaurantListResponse> getRestaurants(){
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        List<RestaurantEntity> restaurantList= restaurantService.getRestaurantByRating();
        for(RestaurantEntity r: restaurantList)
        {

            RestaurantList restaurant= new RestaurantList();
            restaurant.setId(UUID.fromString(r.getUuid()));
            restaurant.restaurantName(r.getRestaurantName());
            restaurant.photoURL(r.getPhotoUrl());
            restaurant.averagePrice(r.getAvgPrice());
            restaurant.setCustomerRating(BigDecimal.valueOf(r.getCustomerRating()));
            restaurant.numberCustomersRated(r.getNoOfCustomerRated());
            RestaurantDetailsResponseAddress address=new RestaurantDetailsResponseAddress();
            address.id(UUID.fromString(r.getAddressEntity().getUuid()));
            address.flatBuildingName(r.getAddressEntity().getFlatBuilNumber());
            address.locality(r.getAddressEntity().getLocality());
            address.city(r.getAddressEntity().getCity());
            address.pincode(r.getAddressEntity().getPinCode());
            RestaurantDetailsResponseAddressState state= new RestaurantDetailsResponseAddressState();
            state.id(UUID.fromString(r.getAddressEntity().getState().getUuid()));
            state.stateName(r.getAddressEntity().getState().getStateName());
            address.state(state);
            restaurant.address(address);
            List<String> categoryLists = new ArrayList();
            for (CategoryEntity categoryEntity :r.getCategory()) {
                categoryLists.add(categoryEntity.getCategoryName());
            }

            // Sorting category list on name
            Collections.sort(categoryLists);
            restaurant.categories(categoryLists.toString());
            restaurantListResponse.addRestaurantsItem(restaurant);
        }
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{restaurantName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity <RestaurantListResponse> getMatchingRestaurantByName(@PathVariable("restaurantName") final String restaurantName) throws RestaurantNotFoundException {

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        List<RestaurantEntity> restaurantList= restaurantService.getMatchingRestaurantByName(restaurantName);
        for(RestaurantEntity r: restaurantList)
        {
            RestaurantList restaurant= new RestaurantList();
            restaurant.setId(UUID.fromString(r.getUuid()));
            restaurant.restaurantName(r.getRestaurantName());
            restaurant.photoURL(r.getPhotoUrl());
            restaurant.averagePrice(r.getAvgPrice());
            restaurant.setCustomerRating(BigDecimal.valueOf(r.getCustomerRating()));
            restaurant.numberCustomersRated(r.getNoOfCustomerRated());
            RestaurantDetailsResponseAddress address=new RestaurantDetailsResponseAddress();
            address.id(UUID.fromString(r.getAddressEntity().getUuid()));
            address.flatBuildingName(r.getAddressEntity().getFlatBuilNumber());
            address.locality(r.getAddressEntity().getLocality());
            address.city(r.getAddressEntity().getCity());
            address.pincode(r.getAddressEntity().getPinCode());
            RestaurantDetailsResponseAddressState state= new RestaurantDetailsResponseAddressState();
            state.id(UUID.fromString(r.getAddressEntity().getState().getUuid()));
            state.stateName(r.getAddressEntity().getState().getStateName());
            address.state(state);
            restaurant.address(address);
            List<String> categoryLists = new ArrayList();
            for (CategoryEntity categoryEntity :r.getCategory()) {
                categoryLists.add(categoryEntity.getCategoryName());
            }

            // Sorting category list on name
            Collections.sort(categoryLists);
            restaurant.categories(categoryLists.toString());
            restaurantListResponse.addRestaurantsItem(restaurant);

        }
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/restaurant/{restaurantId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <RestaurantListResponse>  getRestaurantById(@PathVariable("restaurantId") String restaurantId) throws RestaurantNotFoundException {
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        List<RestaurantEntity>  restaurantList= restaurantService.getRestaurantById(restaurantId);
        for(RestaurantEntity r: restaurantList)
        {

            RestaurantList restaurant= new RestaurantList();
            restaurant.setId(UUID.fromString(r.getUuid()));
            restaurant.restaurantName(r.getRestaurantName());
            restaurant.photoURL(r.getPhotoUrl());
            restaurant.averagePrice(r.getAvgPrice());
            restaurant.setCustomerRating(BigDecimal.valueOf(r.getCustomerRating()));
            restaurant.numberCustomersRated(r.getNoOfCustomerRated());
            RestaurantDetailsResponseAddress address=new RestaurantDetailsResponseAddress();
            address.id(UUID.fromString(r.getAddressEntity().getUuid()));
            address.flatBuildingName(r.getAddressEntity().getFlatBuilNumber());
            address.locality(r.getAddressEntity().getLocality());
            address.city(r.getAddressEntity().getCity());
            address.pincode(r.getAddressEntity().getPinCode());
            RestaurantDetailsResponseAddressState state= new RestaurantDetailsResponseAddressState();
            state.id(UUID.fromString(r.getAddressEntity().getState().getUuid()));
            state.stateName(r.getAddressEntity().getState().getStateName());
            address.state(state);
            restaurant.address(address);
            List<String> categoryLists = new ArrayList();
            for (CategoryEntity categoryEntity :r.getCategory()) {
                categoryLists.add(categoryEntity.getCategoryName());
            }

            // Sorting category list on name
            Collections.sort(categoryLists);
            restaurant.categories(categoryLists.toString());
            restaurantListResponse.addRestaurantsItem(restaurant);
        }
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <RestaurantListResponse>  getRestaurantByCategoryId(@PathVariable("category_id") String categoryId) throws  CategoryNotFoundException {
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        List<RestaurantCategoryEntity>  restaurantCategoryEntities= restaurantService.getRestaurantByCategoryId(categoryId);
        for(RestaurantCategoryEntity restaurantCategoryEntity: restaurantCategoryEntities)
        {
            RestaurantEntity r= restaurantCategoryEntity.getRestaurant();
            RestaurantList restaurant= new RestaurantList();
            restaurant.setId(UUID.fromString(r.getUuid()));
            restaurant.restaurantName(r.getRestaurantName());
            restaurant.photoURL(r.getPhotoUrl());
            restaurant.averagePrice(r.getAvgPrice());
            restaurant.setCustomerRating(BigDecimal.valueOf(r.getCustomerRating()));
            restaurant.numberCustomersRated(r.getNoOfCustomerRated());
            RestaurantDetailsResponseAddress address=new RestaurantDetailsResponseAddress();
            address.id(UUID.fromString(r.getAddressEntity().getUuid()));
            address.flatBuildingName(r.getAddressEntity().getFlatBuilNumber());
            address.locality(r.getAddressEntity().getLocality());
            address.city(r.getAddressEntity().getCity());
            address.pincode(r.getAddressEntity().getPinCode());
            RestaurantDetailsResponseAddressState state= new RestaurantDetailsResponseAddressState();
            state.id(UUID.fromString(r.getAddressEntity().getState().getUuid()));
            state.stateName(r.getAddressEntity().getState().getStateName());
            address.state(state);
            restaurant.address(address);
            List<String> categoryLists = new ArrayList();
            for (CategoryEntity categoryEntity :r.getCategory()) {
                categoryLists.add(categoryEntity.getCategoryName());
            }

            // Sorting category list on name
            Collections.sort(categoryLists);
            restaurant.categories(categoryLists.toString());
            restaurantListResponse.addRestaurantsItem(restaurant);
        }
        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/api/restaurant/{restaurant_id}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantRating(@RequestHeader("authorization") final String authorization,@PathVariable("restaurant_id") final String restaurant_id, final double ratings) throws RestaurantNotFoundException, InvalidRatingException, AuthorizationFailedException {
        RestaurantEntity updatedRestaurantEntity=restaurantService.updateRestaurantRating(restaurant_id,ratings,authorization);
        RestaurantUpdatedResponse restaurantUpdatedResponse= new RestaurantUpdatedResponse().status("RESTAURANT RATING UPDATED SUCCESSFULLY").id(UUID.fromString(updatedRestaurantEntity.getUuid()));
        return new ResponseEntity<RestaurantUpdatedResponse>(restaurantUpdatedResponse, HttpStatus.OK);
    }
}
