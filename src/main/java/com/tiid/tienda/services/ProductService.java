package com.tiid.tienda.services;

import com.tiid.tienda.Utilities.AmazonUtils;
import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.CategoryRepository;
import com.tiid.tienda.repository.ProductRepository;
import com.tiid.tienda.requests.ProductRequest;
import com.tiid.tienda.responses.ProductIdRequest;
import com.tiid.tienda.responses.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
//    private final StorageRepository storageRepositoryImp;
    private final StorageService storageService;

    @Autowired
    private AmazonUtils amazonUtils;

    @Autowired
    public ProductService (ProductRepository productRepository, CategoryRepository categoryRepository, StorageService storageService){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.storageService = storageService;
//        this.storageRepositoryImp = storageRepositoryImp;

    }


    public Page<ProductIdRequest> getAllProducts(Pageable pageable){
        Page<Product> page = productRepository.findAll(pageable);
        List<ProductIdRequest> productIdRequest = new ArrayList<>();

        for(Product product: page){
            ProductIdRequest response = new ProductIdRequest(product);
            productIdRequest.add(response);
            //corregir lo de imagenes
            byte[] photo = amazonUtils.getObject(product.getImg1());
            System.out.println("imagen"+photo);
            response.setImg1(photo);

        }

        Pageable pageableproductIdRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return new PageImpl<>(productIdRequest, pageableproductIdRequest, productIdRequest.size());

    }

    public ProductIdRequest getProductById(long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Product", "id", Long.toString(id)));

        ProductIdRequest productIdRequest = new ProductIdRequest(product);

        byte[] photo = amazonUtils.getObject(product.getImg1());
        productIdRequest.setImg1(photo);

        if(product.getImg2() != null){
            byte[] photo2 = amazonUtils.getObject(product.getImg2());
            productIdRequest.setImg2(photo2);
        }
        if(product.getImg3() != null){
            byte[] photo3 = amazonUtils.getObject(product.getImg3());
            productIdRequest.setImg3(photo3);
        }
        if(product.getImg4() != null){
            byte[] photo4 = amazonUtils.getObject(product.getImg4());
            productIdRequest.setImg4(photo4);
        }
        if(product.getVid() != null){
            byte[] video = amazonUtils.getObject(product.getVid());
            productIdRequest.setVid(video);
        }


        return productIdRequest;
        }

    public ProductResponse
    createProduct (ProductRequest request, MultipartFile photo1, MultipartFile photo2, MultipartFile photo3, MultipartFile photo4, MultipartFile video){

        String producPhoto = request.getName() + "-" + request.getDescription();

       // Category category = categoryRepository.findById(request.getCategory_id()).orElseThrow(() -> new ResourceNotFoundExecption("category", "id",  Long.toString(request.getCategory_id())));

//        List<Category> categories = new ArrayList<>();
//        categories.add(category);

//        storageService.uploadFile("", "ejemplo", request.getImg1());

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCost(request.getCost());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        //product.setCategories(categories);
//        product.setStock(request.getStock());
        product.setLowstock(request.getLowstock());

        String producPhoto1 = producPhoto + "-" + UUID.randomUUID().toString();
        amazonUtils.putObject(producPhoto1, photo1);
        product.setImg1(producPhoto1);

        if(request.getImg2() != null){
            String producPhoto2 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto2, photo2);
            product.setImg2(producPhoto2);
        }
        if (request.getImg3() != null) {
            String producPhoto3 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto3, photo3);
            product.setImg3(producPhoto3);
        }
        if(request.getImg4() != null){
            String producPhoto4 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto4, photo4);
            product.setImg4(producPhoto4);
        }
        if(request.getVid() != null){
            String producVideo = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producVideo, video);
            product.setVid(producVideo);
        }


        Product productSaved = productRepository.save(product);


        return new ProductResponse(productSaved);

     }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Product", "id", Long.toString(id)));
        amazonUtils.delete(product.getImg1());
        productRepository.deleteById(id);
    }

    public ProductResponse editProduct(ProductRequest request, Long id, MultipartFile photo1, MultipartFile photo2, MultipartFile photo3, MultipartFile photo4, MultipartFile video){

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Product", "id", Long.toString(id)));
        String producPhoto = request.getName() + "-" + request.getDescription();

        if(request.getName() != null){

            product.setName(request.getName());
        }
        if(request.getDescription() != null){
            product.setDescription(request.getDescription());
        }
        if(request.getPrice() != 0){
            product.setPrice(request.getPrice());
        }
        if(request.getCost() != 0){
            product.setCost(request.getCost());
        }

        if(request.getQuantity() != 0){
            product.setQuantity(request.getQuantity());
        }

        if(request.getImg1() != null){
            String producPhoto1 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto1, photo1);
            product.setImg1(producPhoto1);
        }


        if(request.getImg2() != null){
            String producPhoto2 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto2, photo2);
            product.setImg2(producPhoto2);
        }
        if (request.getImg3() != null) {
            String producPhoto3 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto3, photo3);
            product.setImg3(producPhoto3);
        }
        if(request.getImg4() != null){
            String producPhoto4 = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producPhoto4, photo4);
            product.setImg4(producPhoto4);
        }
        if(request.getVid() != null){
            String producVideo = producPhoto + "-" + UUID.randomUUID().toString();
            amazonUtils.putObject(producVideo, video);
            product.setVid(producVideo);
        }

        Product productSaved = productRepository.save(product);

        return new ProductResponse(productSaved);


    }

    public List<ProductResponse> searchProduct (String nameToSearch){
        List<Product> products = productRepository.findProduct(nameToSearch);
        List<ProductResponse> productsResponses = new ArrayList<>();


        for(Product product: products){
            ProductResponse response = new ProductResponse(product);
            productsResponses.add(response);
        }

        return productsResponses;
    }


}
