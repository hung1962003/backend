package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.CategoryRequest;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class CategoryAPI {
    @Autowired
        CategoryService categoryService;
    @PostMapping()
    public ResponseEntity add(@RequestBody CategoryRequest newcategory){
        Category category =categoryService.add(newcategory);
        return ResponseEntity.ok(category);
    }
    @GetMapping()
    public ResponseEntity getCategorys(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
    @GetMapping("{id}")
    public ResponseEntity getCategory(@PathVariable int id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteCategory(@PathVariable int id){

        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
    @PutMapping("{id}")
    public ResponseEntity updateCategory(@PathVariable int id,@RequestBody CategoryRequest newcategory){

        return ResponseEntity.ok( categoryService.updateCategory(id,newcategory));
    }
}
