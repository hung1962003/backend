package store.auroraauction.be.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.auroraauction.be.Models.RegisterCategoryRequest;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.repository.CategoryRepository;
import store.auroraauction.be.service.CategoryService;

@RestController
public class CategoryAPI {
    @Autowired
        CategoryService categoryService;
    @PostMapping("/dangki")
    public ResponseEntity dangki(@RequestBody RegisterCategoryRequest registerCategoryRequest){
        Category category =categoryService.registered(registerCategoryRequest);
        return ResponseEntity.ok(category);
    }
    @GetMapping("/danhsach")
    public ResponseEntity danhsach(){
        return ResponseEntity.ok(categoryService.getAll());
    }
}
