package com.tiid.tienda.services;

import com.tiid.tienda.entities.*;
import com.tiid.tienda.exceptions.GeneralExpection;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.exceptions.ShippingError;
import com.tiid.tienda.repository.*;
import com.tiid.tienda.requests.ItsOkOrderRequest;
import com.tiid.tienda.requests.OrderRequest;
import com.tiid.tienda.requests.ProductOrderCheckRequest;
import com.tiid.tienda.requests.SoldProductRequest;
import com.tiid.tienda.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final ShippingRepository shippingRepository;

    private final OrderDetailsService orderDetailsService;

    private final SoldProductService soldProductService;

    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ShippingRepository shippingRepository, OrderDetailsService orderDetailsService, SoldProductService soldProductService, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shippingRepository = shippingRepository;
        this.orderDetailsService = orderDetailsService;
        this.soldProductService = soldProductService;
        this.productRepository = productRepository;
    }


    public void createOrder(OrderRequest orderRequest){
        Order order = saveOrder(orderRequest);
        double total = getTotalToAOrder(orderRequest.getSoldProductRequests());
        OrderDetails orderDetails = orderDetailsService.createOrderDetails(order, total);
        soldProductService.saveProductSold(orderDetails, orderRequest.getSoldProductRequests());
    }


    public List<Order> getAllOrdersInfoUser(String email){

        return orderRepository.findByUser_Email(email);

    }

    public List<OrderResponse> getAllOrdersUser(String email){

        List<Order> orderList = orderRepository.findByUser_Email(email);
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for(Order order: orderList){
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setEmailUser(email);
            orderResponse.setDateCreated(order.getDateCreated());
            orderResponse.setDateShipped(order.getDateShipped());
            orderResponse.setStatus(order.getStatus());

            orderResponse.setShippingResponse(createAShippingResponse(order.getShipping(), email));
            orderResponse.setId(order.getId());

            orderResponseList.add(orderResponse);
        }


        return orderResponseList;

    }



    private Order saveOrder(OrderRequest orderRequest){
        Order order = new Order();

        User user = userRepository.findUserByEmail(orderRequest.getEmailUser()).orElseThrow(() -> new ResourceNotFoundExecption("user", "email", orderRequest.getEmailUser()));
        Shipping shipping = shippingRepository.findById(orderRequest.getShippingId()).orElseThrow(() -> new ResourceNotFoundExecption("Shipping", "id", Long.toString(orderRequest.getShippingId())));
        LocalDate today = LocalDate.now();
        String address = shipping.getCity() + " " + shipping.getStreet() + " " + " #" + shipping.getNumber() + " CP: " + shipping.getCp();

        order.setUser(user);
        order.setDateCreated(today);
        order.setStatus("PREPARATION");
        order.setShipping(shipping);
        order.setPaypalBougthId(orderRequest.getPaypalBougthId());
        order.setAddress(address);

        return orderRepository.save(order);

    }


    private double getTotalToAOrder(List<SoldProductRequest> soldProducts){
        double total = 0;

        for(SoldProductRequest soldProductRequest: soldProducts){
            Product product = productRepository.findById(soldProductRequest.getProductID()).orElseThrow(() -> new ResourceNotFoundExecption("product", "id", Long.toString(soldProductRequest.getProductID())));
            if(product.getPriceWithDiscount() != null){
                total += product.getPriceWithDiscount() * soldProductRequest.getQuantity();
            } else{
                total += product.getPrice() * soldProductRequest.getQuantity();
            }
        }

        return total;
    }



    private ShippingResponse createAShippingResponse(Shipping shipping, String emailUser){
        ShippingResponse response = new ShippingResponse();

        response.setEmailUser(emailUser);
        response.setName(shipping.getName());
        response.setState(shipping.getState());
        response.setCity(shipping.getCity());
        response.setStreet(shipping.getStreet());
        response.setNumber(shipping.getNumber());
        response.setInternalNumber(shipping.getInternalNumber());
        response.setCologne(shipping.getCologne());
        response.setReferencesHouse(shipping.getReferencesHouse());
        response.setCp(shipping.getCp());


        return response;


    }




    public List<DashboardOrdersResponse> getAllOrders(Pageable pageableRequest){
        Pageable pageable = PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(),Sort.by("dateCreated").ascending());

        Page<Order> orders = orderRepository.findAll(pageable);
        List<DashboardOrdersResponse> ordersResponses = new ArrayList<>();

        for(Order order: orders){
            DashboardOrdersResponse ordersResponse = new DashboardOrdersResponse(order);
            ordersResponses.add(ordersResponse);
        }

        return ordersResponses;
    }

    public OrderTotalResponse getDetailOrderDashboard(Long idOrder){
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new ResourceNotFoundExecption("product", "id", Long.toString(idOrder)));
        return new OrderTotalResponse(order);
    }




    public Boolean checkBeforeOrder(ItsOkOrderRequest request){
        String messages = checkProducts(request.getProductsRequest());

        if(messages.length() != 0){
            System.out.println(messages);
            throw new GeneralExpection(messages);

        }else{

            return true;
        }


    }

    private String checkProducts(List<ProductOrderCheckRequest> productOrderCheckRequestList){
        StringBuilder messages = new StringBuilder();
        for(ProductOrderCheckRequest productActually: productOrderCheckRequestList){
            Product product = productRepository.findById(productActually.getId()).orElseThrow(() -> new ResourceNotFoundExecption("product", "id", Long.toString(productActually.getId())));

            if(!productActually.getPrice().equals(product.getPrice())){
                messages.append("\n Product: ").append(product.getName()).append(" its not equal with request's price. ").append(" Price actually: ").append(product.getPrice()).append(" request's price: ").append(productActually.getPrice());
            }
            if(product.getQuantity() - productActually.getQuantity() < 0){
                messages.append("\n Product: ").append(product.getName()).append(" there are not enough units ").append(" Unit actually: ").append(product.getQuantity()).append(" request's quantity: ").append(productActually.getQuantity());
            }

        }

        return messages.toString();
    }




}
