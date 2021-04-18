package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import com.upgrad.FoodOrderingApp.service.model.ItemQuantity;
import com.upgrad.FoodOrderingApp.service.model.SaveOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    CustomerService customerService;

    @Autowired
    AddressService addressService;

    @Autowired
    CustomerAddressDao customerAddressDao;

    @Autowired
    CouponService couponService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    ItemService itemService;
    @Transactional
    public List<OrderEntity> getCustomerOrders(final String authorization) throws AuthorizationFailedException {

        CustomerEntity customerEntity= customerService.getCustomer(authorization);
        return orderDao.getCustomerOrders(customerEntity);
    }

    @Transactional
    public OrderEntity saveOrder(final SaveOrderRequest saveOrderRequest, final String authorizationToken)
            throws AuthorizationFailedException, AddressNotFoundException, RestaurantNotFoundException, CouponNotFoundException, PaymentMethodNotFoundException, ItemNotFoundException {

        CustomerEntity customerEntity = customerService.getCustomer(authorizationToken);
        AddressEntity addressEntity = addressService.getAddressByUUID(saveOrderRequest.getAddressId(), customerEntity);
        if (addressEntity == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }
        CustomerAddressEntity customerAddressEntity = customerAddressDao.getCustAddressByCustIdAddressId(customerEntity, addressEntity);
        if (customerAddressEntity == null) {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }
        CouponEntity couponEntity = couponService.getCouponByUuid(saveOrderRequest.getCouponId().toString());
        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-002", "No coupon by this id");
        }
        PaymentEntity paymentEntity = paymentService.getPaymentByUuid(saveOrderRequest.getPaymentId().toString());
        if (paymentEntity == null) {
            throw new PaymentMethodNotFoundException("PNF-002", "No payment method found by this id");
        }
        RestaurantEntity restaurantEntity = restaurantService.getRestaurantById(saveOrderRequest.getRestaurantId().toString()).get(0);
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        final ZonedDateTime now = ZonedDateTime.now();
        OrderEntity ordersEntity = new OrderEntity();
        ordersEntity.setUuid(UUID.randomUUID().toString());
        ordersEntity.setCoupon(couponEntity);
        ordersEntity.setRestaurant(restaurantEntity);
        ordersEntity.setPayment(paymentEntity);
        ordersEntity.setCustomer(customerEntity);
        ordersEntity.setAddress(addressEntity);
        ordersEntity.setBill(saveOrderRequest.getBill());
        ordersEntity.setDiscount(saveOrderRequest.getDiscount());
        ordersEntity.setDate(now);

        OrderEntity savedOrderEntity = orderDao.saveOrder(ordersEntity);


        for (ItemQuantity itemQuantity : saveOrderRequest.getItemQuantities()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrders(savedOrderEntity);
            orderItemEntity.setItem(itemService.getItemEntityByUuid(itemQuantity.getItemId().toString()));
            orderItemEntity.setQuantity(itemQuantity.getQuantity());
            orderItemEntity.setPrice(itemQuantity.getPrice());

            orderItemDao.createOrderItemEntity(orderItemEntity);
        }


        return orderDao.saveOrder(savedOrderEntity);
    }
}
