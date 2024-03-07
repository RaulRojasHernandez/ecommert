package com.tiid.tienda.services;

import com.tiid.tienda.entities.Order;
import com.tiid.tienda.entities.OrderDetails;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.entities.SoldProducts;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.OrderDetailsRepository;
import com.tiid.tienda.repository.SoldProductsRepository;
import com.tiid.tienda.responses.OrderDetailsResponse;
import com.tiid.tienda.responses.SoldProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final SoldProductsRepository soldProductsRepository;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository, SoldProductsRepository soldProductsRepository){
        this.orderDetailsRepository = orderDetailsRepository;
        this.soldProductsRepository = soldProductsRepository;
    }

    public OrderDetails createOrderDetails(Order order, double total){
        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setOrder(order);
        orderDetails.setTotal(total);

        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetailsResponse getOrderDetailsResponse(String emailUser, Long idOrder){
        OrderDetails orderDetails = orderDetailsRepository.findOrderDetailByEmailUserAndOrderId(emailUser, idOrder);
        if(orderDetails == null){
            throw new ResourceNotFoundExecption("Order deails", "id", Long.toString(idOrder));
        }else{
            OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
            orderDetailsResponse.setIdOrder(orderDetails.getOrder().getId());
            orderDetailsResponse.setTotal(orderDetails.getTotal());
            orderDetailsResponse.setSoldProductsResponse(createListProductSold(orderDetails.getSoldProducts()));

            return orderDetailsResponse;
        }
    }

//    public List<SoldProductsResponse> getOrderPending(){
//        OrderDetails orderDetails = orderDetailsRepository.findallOrderDetail();
//
//    }





    private List<SoldProductsResponse> createListProductSold(List<SoldProducts> list){
        List<SoldProductsResponse> soldProductsResponses = new ArrayList<>();

        for(SoldProducts soldProduct: list){
            SoldProductsResponse response = new SoldProductsResponse();

            Product product = soldProduct.getProduct();

            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setPriceWithDiscount(product.getPriceWithDiscount());
            response.setQuantity(soldProduct.getQuantity());
            response.setImg1(product.getImg1());

            soldProductsResponses.add(response);
        }

        return soldProductsResponses;
    }




}
