package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CouponService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin
public class OrderController {

    @Autowired
    CouponService couponService;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCoupon(@RequestHeader("authorization")  final String authorization, @PathVariable("coupon_name") final String couponName) throws AuthorizationFailedException, CouponNotFoundException {
        CouponEntity couponEntity=    couponService.getCouponByName(couponName,authorization);
        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse().id(UUID.fromString(couponEntity.getUuid()))
                .couponName(couponEntity.getCouponName()).percent(couponEntity.getPercent());
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrders(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
        List<OrderEntity> ordersEntityList = orderService.getCustomerOrders(authorization);
        List<OrderList> orderDetailsList = new ArrayList<OrderList>();

        for (OrderEntity ordersEntity : ordersEntityList) {

            OrderListCustomer orderListCustomer = new OrderListCustomer();
            orderListCustomer.setId(UUID.fromString(ordersEntity.getCustomer().getUuid()));
            orderListCustomer.setFirstName(ordersEntity.getCustomer().getFirstName());
            orderListCustomer.setLastName(ordersEntity.getCustomer().getLastName());
            orderListCustomer.setContactNumber(ordersEntity.getCustomer().getContactNum());
            orderListCustomer.setEmailAddress(ordersEntity.getCustomer().getEmail());

            OrderListAddressState orderListAddressState = new OrderListAddressState();
            orderListAddressState.setId(UUID.fromString(ordersEntity.getAddress().getState().getUuid()));
            orderListAddressState.setStateName(ordersEntity.getAddress().getState().getStateName());

            OrderListAddress orderListAddress = new OrderListAddress();
            orderListAddress.setId(UUID.fromString(ordersEntity.getAddress().getUuid()));
            orderListAddress.setFlatBuildingName(ordersEntity.getAddress().getFlatBuilNumber());
            orderListAddress.setLocality(ordersEntity.getAddress().getLocality());
            orderListAddress.setCity(ordersEntity.getAddress().getCity());
            orderListAddress.setPincode(ordersEntity.getAddress().getPinCode());
            orderListAddress.setState(orderListAddressState);

            OrderListCoupon orderListCoupon = new OrderListCoupon();
            orderListCoupon.setId(UUID.fromString(ordersEntity.getCoupon().getUuid()));
            orderListCoupon.setCouponName(ordersEntity.getCoupon().getCouponName());
            orderListCoupon.setPercent(ordersEntity.getCoupon().getPercent());

            OrderListPayment orderListPayment = new OrderListPayment();
            orderListPayment.setId(UUID.fromString(ordersEntity.getUuid()));
            orderListPayment.setPaymentName(ordersEntity.getPayment().getPaymentName());

            OrderList orderList = new OrderList();
            orderList.setId(UUID.fromString(ordersEntity.getUuid()));
            orderList.setDate(ordersEntity.getDate().toString());
            orderList.setAddress(orderListAddress);
            orderList.setCustomer(orderListCustomer);
            orderList.setPayment(orderListPayment);
            orderList.setCoupon(orderListCoupon);
            orderList.setBill(ordersEntity.getBill());
            orderList.setDiscount(ordersEntity.getDiscount());

           for (OrderItemEntity orderItemEntity : itemService.getItemsByOrder(ordersEntity)) {

                ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem();
                itemQuantityResponseItem.setId(UUID.fromString(orderItemEntity.getItem().getUuid()));
                itemQuantityResponseItem.setItemName(orderItemEntity.getItem().getItemName());
                itemQuantityResponseItem.setItemPrice(orderItemEntity.getItem().getPrice());
                itemQuantityResponseItem.setType(ItemQuantityResponseItem.TypeEnum.valueOf(orderItemEntity.getItem().getType().toString()));

                ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse();
                itemQuantityResponse.setItem(itemQuantityResponseItem);
                itemQuantityResponse.setPrice(orderItemEntity.getPrice());
                itemQuantityResponse.setQuantity(orderItemEntity.getQuantity());

                orderList.addItemQuantitiesItem(itemQuantityResponse);
            }
            customerOrderResponse.addOrdersItem(orderList);
        }
        return new ResponseEntity<CustomerOrderResponse>(customerOrderResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(@RequestHeader("authorization") final String authorization, final SaveOrderRequest saveOrderRequest) throws PaymentMethodNotFoundException, AuthorizationFailedException, RestaurantNotFoundException, CouponNotFoundException, AddressNotFoundException, ItemNotFoundException {
        com.upgrad.FoodOrderingApp.service.model.SaveOrderRequest request= new com.upgrad.FoodOrderingApp.service.model.SaveOrderRequest();
        request.setAddressId(saveOrderRequest.getAddressId());
        request.setBill(saveOrderRequest.getBill());
        request.setCouponId(saveOrderRequest.getCouponId());
        request.setDiscount(saveOrderRequest.getDiscount());
        request.setPaymentId(saveOrderRequest.getPaymentId());
        request.setRestaurantId(saveOrderRequest.getRestaurantId());
        List <com.upgrad.FoodOrderingApp.service.model.ItemQuantity> itemQuantityList = new ArrayList<>();
        for( ItemQuantity item: saveOrderRequest.getItemQuantities())
        {
            com.upgrad.FoodOrderingApp.service.model.ItemQuantity itemQuantity = new  com.upgrad.FoodOrderingApp.service.model.ItemQuantity();
            itemQuantity.setItemId(item.getItemId());
            itemQuantity.setQuantity(item.getQuantity());
            itemQuantity.setPrice(item.getPrice());
        }
        request.setItemQuantities(itemQuantityList);
        OrderEntity savedOrderEntity = orderService.saveOrder(request, authorization);
        SaveOrderResponse saveOrderResponse = new SaveOrderResponse().id("")
                .status("ORDER SUCCESSFULLY PLACED");
        return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.CREATED);
    }

}
