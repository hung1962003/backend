package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.RegisterCategoryRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.repository.CategoryRepository;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public Category registered(RegisterCategoryRequest registerCategoryRequest){
        Category category = new Category();
        category.setName(registerCategoryRequest.getName());
        categoryRepository.save(category);
        return category;
    }


    public List<Category> getAll(){
        List<Category> accounts = categoryRepository.findAll();
        return accounts;
    }
}
