package com.tiid.tienda.services;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.CategoryRepository;
import com.tiid.tienda.requests.CategoryRequest;
import com.tiid.tienda.responses.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this. categoryRepository = categoryRepository;
    }


    public CategoryResponse getCategoryById(long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("category", "id", Long.toString(id)));
        return new CategoryResponse(category);
    }

    public Page<CategoryResponse> getAllCategories(Pageable pageable){
        Page<Category> page = categoryRepository.findAll(pageable);
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for(Category category: page){
            CategoryResponse response = new CategoryResponse(category);
            categoryResponses.add(response);
        }
        Pageable pageableCategoriesResponse = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return new PageImpl<>(categoryResponses, pageableCategoriesResponse,categoryResponses.size());
    }

    public void createCategory(CategoryRequest request){

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImg("ejemplo/img/category");

        categoryRepository.save(category);

    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);

    }

    public CategoryResponse editCategory(CategoryRequest request, Long id ){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("category", "id", Long.toString(id)));

        if (request.getName() != null){
            category.setName(request.getName());
        }
        if(request.getDescription() != null){
            category.setDescription(request.getDescription());
        }


        Category categorySaved = categoryRepository.save(category);

        return new CategoryResponse(categorySaved);

    }

    public List<CategoryResponse> searchCategories(String nameToSearch){
        List<Category> categories = categoryRepository.findCategory(nameToSearch);
        List<CategoryResponse> responses = new ArrayList<>();

        for(Category category: categories){
            CategoryResponse response = new CategoryResponse(category);
            responses.add(response);
        }

        return responses;
    }


}
