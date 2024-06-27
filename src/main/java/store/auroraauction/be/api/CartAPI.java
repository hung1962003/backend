package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import store.auroraauction.be.Models.CategoryRequest;
import store.auroraauction.be.entity.Cart;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.service.CartService;

@RestController
@RequestMapping("/api/cart")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class CartAPI {
    @Autowired
    CartService cartService;

    @GetMapping()
    public ResponseEntity getCarts(){

        return ResponseEntity.ok(cartService.getCart2());
    }
    @GetMapping("{id}")
    public ResponseEntity getCart(@PathVariable Long id){
        return ResponseEntity.ok(cartService.getCart(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteCart(@PathVariable long id){

        return ResponseEntity.ok(cartService.deleteCart(id));
    }

    @PostMapping("addtocart/{jewelry_id}")
    public ResponseEntity updateCart(@PathVariable Long jewelry_id){

        return ResponseEntity.ok( cartService.addJewelryIntoCart(jewelry_id));
    }
    @DeleteMapping()
    public ResponseEntity deleteAllCart(@PathVariable long id){

        return ResponseEntity.ok(cartService.deleteAllCart());
    }
    //    @PostMapping("")
//    public ResponseEntity add(@RequestBody CartDTO newcart){
//        Cart cart =cartService.add(newcart);
//        return ResponseEntity.ok(cart);
//    }
}


