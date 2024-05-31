package store.auroraauction.be.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryAPI {
    @Autowired
        CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Category newcategory){
        Category category =categoryService.add(newcategory);
        return ResponseEntity.ok(category);
    }
    @GetMapping("/Categorys")
    public ResponseEntity getCategorys(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
    @GetMapping("/Category/{id}")
    public ResponseEntity getCategory(@PathVariable int id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }
    @DeleteMapping("/Category/{id}")
    public ResponseEntity deleteCategory(@PathVariable int id){

        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
    @PutMapping("/Category/{id}")
    public ResponseEntity updateCategory(@PathVariable int id,@RequestBody Category newcategory){

        return ResponseEntity.ok( categoryService.updateCategory(id,newcategory));
    }
}
