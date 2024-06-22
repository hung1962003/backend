package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.auroraauction.be.Models.CategoryRequest;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.repository.CategoryRepository;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public Category add( CategoryRequest newcategory ) {
        Category category = new Category();
        category.setCategory_name(newcategory.getCategory_name());
        categoryRepository.save(category);
        return category;
    }
    public String deleteCategory(int id) {
        categoryRepository.deleteById(id) ;
        return "category delteted";
    }

    public List<Category> getAllCategory(){
        List<Category> category = categoryRepository.findAll();
        return category;
    }
    public Category getCategory( int id) {
        Category category = categoryRepository.findById(id).get();
        return category;
    }
    public Category updateCategory(int id, CategoryRequest newcategory) {
        Category category = categoryRepository.findById(id).get();
        category.setCategory_name(newcategory.getCategory_name());
        categoryRepository.save(category);
        return category;
    }
}
