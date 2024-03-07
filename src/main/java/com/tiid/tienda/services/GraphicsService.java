package com.tiid.tienda.services;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.OrderDetails;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.entities.SoldProducts;
import com.tiid.tienda.repository.OrderRepository;
import com.tiid.tienda.repository.SoldProductsRepository;
import com.tiid.tienda.repository.UserRepository;
import com.tiid.tienda.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphicsService {


    private SoldProductsRepository soldProductsRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public GraphicsService(SoldProductsRepository soldProductsRepository, UserRepository userRepository, OrderRepository orderRepository){
        this.soldProductsRepository = soldProductsRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    public SalesResponse getSalesByMonth(LocalDate startDate, LocalDate endDate) {
        if(startDate == null){
            startDate = LocalDate.now();
            startDate = startDate.minusMonths(3);
        }
        if(endDate == null){
            endDate = LocalDate.now();
        }


        List<Map<String, Object>> salesByMonth = orderRepository.getSalesByMonth(startDate, endDate);

        Map<String, Map<String, Double>> salesMap = salesByMonth.stream()
                .filter(entry -> entry.get("year") != null &&
                        entry.get("month") != null &&
                        entry.get("totalAmount") != null &&
                        entry.get("month") instanceof Integer &&
                        entry.get("totalAmount") instanceof Double)
                .collect(Collectors.groupingBy(
                        entry -> String.valueOf(entry.get("year")),
                        Collectors.toMap(
                                entry -> String.format("%02d", (Integer) entry.get("month")),
                                entry -> (Double) entry.get("totalAmount")
                        )
                ));

        return SalesResponse.builder().sales(salesMap).build();
    }


    public GraphicResponse getAllData(LocalDate startDate, LocalDate endDate){
        if(startDate == null){
            startDate = LocalDate.now();
            startDate = startDate.minusMonths(3);
        }
        if(endDate == null){
            endDate = LocalDate.now();
        }

        Product product = soldProductsRepository.findMostSoldProduct(startDate, endDate);
        Double total = soldProductsRepository.getTotalSalesAmount(startDate, endDate);
        List<Category> categories = soldProductsRepository.findTop10SellingCategories(startDate, endDate);
        List<Product> products = soldProductsRepository.findTop10SellingProducts(startDate, endDate);
        List<Product> productsBottom = soldProductsRepository.findBottom10SellingProducts(startDate, endDate);
        Integer countSalesByOrderDetails = soldProductsRepository.countSalesByOrderDetails(startDate, endDate);
        Integer totalEmployees = userRepository.countEmployees();
        Integer totalBuyers = userRepository.countBuyers();


        ProductResponse productResponse = null;
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        List<ProductResponse> productsResponses = new ArrayList<>();
        List<ProductResponse> productsBottomResponses = new ArrayList<>();


        if(product != null){
            productResponse = new ProductResponse(product);
        }
        if(categories.size() > 0){
            categoryResponses = transformCategoriesResponse(categories);
        }
        if(products.size() > 0){
            productsResponses = transformProductsResponse(products);
        }
        if(productsBottom.size() > 0){
            productsBottomResponses = transformProductsResponse(productsBottom);
        }




        return new GraphicResponse(productResponse, total, categoryResponses, productsResponses, productsBottomResponses, countSalesByOrderDetails, totalEmployees, totalBuyers);


    }


    public List<CategoryResponse> transformCategoriesResponse(List<Category> categories){
        List<CategoryResponse> responses = new ArrayList<>();
        for(Category category: categories){
            CategoryResponse response = new CategoryResponse(category);
            responses.add(response);
        }

        return  responses;
    }

    public List<ProductResponse> transformProductsResponse(List<Product> products){
        List<ProductResponse> responses = new ArrayList<>();

        for(Product product: products){
            ProductResponse response = new ProductResponse(product);
            responses.add(response);
        }

        return responses;
    }







}
