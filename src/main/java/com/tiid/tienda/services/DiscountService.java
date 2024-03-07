package com.tiid.tienda.services;

import com.tiid.tienda.Utilities.Utility;
import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.CategoryRepository;
import com.tiid.tienda.repository.DiscountRepository;
import com.tiid.tienda.repository.ProductRepository;
import com.tiid.tienda.requests.DiscountRequest;
import com.tiid.tienda.responses.DiscountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DiscountService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final DiscountRepository discountRepository;

    private final Utility utility;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, ProductRepository productRepository, CategoryRepository categoryRepository, Utility utility){
        this.discountRepository = discountRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.utility = utility;


    }

    public Page<DiscountResponse> getAllDiscounts(Pageable pageable){
        Page<Discount> page =  discountRepository.findAll(pageable);
        List<DiscountResponse> discountResponses  = new ArrayList<>();

        for(Discount discount: page){
            DiscountResponse response = new DiscountResponse(discount);
            discountResponses.add(response);
        }
        PageRequest pageDiscountsReponse = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return new PageImpl<>(discountResponses, pageDiscountsReponse, discountResponses.size());
    }

    public DiscountResponse getDiscountById(long id){
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("discount", "id", Long.toString(id)));
        return new DiscountResponse(discount);
    }


    public DiscountResponse createDiscount(DiscountRequest request){

        List<Product> listProducts = new ArrayList<>();
        List<Category>  listCategories = new ArrayList<>();

        Discount discount = new Discount();

        if(request.getProducts_ids() != null){
            for(int i =0; i <= request.getProducts_ids().length -1; i++){
                Long id = request.getProducts_ids()[i];
                Product product = productRepository.findById(id).orElseThrow();
                setPriceWithDiscount(product, request.getPercentage());
                listProducts.add(product);
            }
            discount.setProducts(listProducts);
        }
       else if(request.getCategories_ids() != null){
           for(int i=0; i <= request.getCategories_ids().length -1 ; i++){
               Long id = request.getCategories_ids()[i];
               Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("category", "id", Long.toString(id)));
               setPriceWithDiscountByCategory(category, request.getPercentage());
               listCategories.add(category);
           }
           discount.setCategories(listCategories);
       }

       if(request.getImg() != null){
//            TODO: Subir correctamente la imagen
           discount.setImg("imagenEjemplo");
       }

       if(request.getVid() != null){
//           TODO: Agregar corectamente el video
           discount.setVid("videoEjemplo");
       }


        discount.setName(request.getName());
        discount.setDescription(request.getDescription());
        discount.setPercentage(request.getPercentage());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setActive(true);

        Discount discountSaved = discountRepository.save(discount);
        return new DiscountResponse(discountSaved);


    }

    public void deleteDiscount(Long id){
        Discount discount = discountRepository.findById(id).orElseThrow();
        if ( discount.getProducts() != null ){
            List<Product> products =  discount.getProducts();
            for(Product product: products){
                removePriceWithDiscount(product);
            }
        }
        if(discount.getCategories() != null){
            List<Category> categories = discount.getCategories();
            for(Category category: categories){
                removePriceWithDiscountByCategory(category);
            }

        }


        discountRepository.deleteById(id);
    }

    public Discount editDiscount(DiscountRequest request, Long id){
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("category", "id", Long.toString(id)));
        List<Product> listProducts = new ArrayList<>();
        List<Category>  listCategories = new ArrayList<>();


        if(request.getName() != null){
            discount.setName(request.getName());
        }

        if(request.getDescription() != null){
            discount.setDescription(request.getDescription());
        }

        if(request.getStartDate() != null){
            discount.setStartDate(request.getStartDate());
        }

        if(request.getEndDate() != null){
            discount.setEndDate(request.getEndDate());
        }

        if(request.getPercentage() != 0){
            discount.setPercentage(request.getPercentage());
        }

        if(request.getProducts_ids() != null){
            for(int i = 0; i <= request.getProducts_ids().length; i++){
                Long idAct = request.getProducts_ids()[i];
                Product product = productRepository.findById(idAct).orElseThrow(() -> new ResourceNotFoundExecption("category", "id", Long.toString(id)));
                listProducts.add(product);

            }
            discount.setProducts(listProducts);

        }

        if(request.getCategories_ids()!= null){
            for(int i = 0; i <= request.getCategories_ids().length; i++){
                Long idAct = request.getCategories_ids()[i];
                Category category = categoryRepository.findById(idAct).orElseThrow(() -> new ResourceNotFoundExecption("category", "id", Long.toString(id)));
                listCategories.add(category);

            }
            discount.setCategories(listCategories);
        }

        discountRepository.save(discount);

        return discount;

    }

    public List<DiscountResponse> searchDiscount(String nameToSearch){
        List<Discount> discounts = discountRepository.findDiscount(nameToSearch);
        List<DiscountResponse> responses = new ArrayList<>();

        for(Discount discount: discounts){
            DiscountResponse response = new DiscountResponse(discount);
            responses.add(response);
        }


        return responses;
    }



    private void setPriceWithDiscount(Product product, double discount){
        product.setPriceWithDiscount(utility.getPriceWithDiscount(product.getPrice(), discount));
        productRepository.save(product);

    }

    private void setPriceWithDiscountByCategory(Category category, double discount){
        List<Product> products = productRepository.findByCategory_Id(category.getId());
        for(int i = 0; i <= products.size() -1 ; i++){
            Product product = products.get(i);
            setPriceWithDiscount(product, discount);
        }



    }

    private void removePriceWithDiscount(Product product){
        product.setPriceWithDiscount(null);
        productRepository.save(product);
    }

    private void removePriceWithDiscountByCategory(Category category){
        List<Product> products = productRepository.findByCategory_Id(category.getId());
        for(int i = 0; i <= products.size() -1; i++){
            Product product = products.get(i);
            removePriceWithDiscount(product);
        }

    }



    @Scheduled(cron = "0 * * * * ")
    private void deactivateDiscounts() {
        LocalDate today = LocalDate.now();
        LocalDate beforeYesterday = today.minusDays(2);

        List<Discount> discounts = discountRepository.findByEndDateBetweenAndActiveIsTrue(beforeYesterday, today);

        for(Discount discount: discounts){

            if(discount.getCategories() != null){
                List<Category> categories = discount.getCategories();
                for(Category category: categories){
                    removePriceWithDiscountByCategory(category);
                }
                discount.setCategories(null);
            }

            if (discount.getProducts() != null) {
                List<Product> products = discount.getProducts();
                for(Product product: products){
                    removePriceWithDiscount(product);
                }
                discount.setProducts(null);

            }

            discount.setActive(false);

            discountRepository.save(discount);
        }




    }

}
