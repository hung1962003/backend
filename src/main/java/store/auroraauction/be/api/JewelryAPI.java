package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import store.auroraauction.be.Models.JewelryRequest;
import store.auroraauction.be.entity.Jewelry;

import store.auroraauction.be.service.JewelryService;

@RestController
@RequestMapping("api/jewelry")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class JewelryAPI
{
    @Autowired
    JewelryService jewelryService;
    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("test ok");
    }

    @PostMapping("")
    public ResponseEntity add(@RequestBody JewelryRequest newjewelry) {
        Jewelry jewelry = jewelryService.addJewelry(newjewelry);
        return ResponseEntity.ok(jewelry);
    }

    @GetMapping("")
    public ResponseEntity getJewelrys() {
        return ResponseEntity.ok(jewelryService.getJewelrys());
    }

    @GetMapping("getJewelryReady")
    public ResponseEntity getJewelryReady() {
        return ResponseEntity.ok(jewelryService.getJewelryReady());
    }

    @GetMapping("findJewelryByNameContaining")
    public ResponseEntity findJewelryByNameContaining(@RequestParam String name) {
        return ResponseEntity.ok(jewelryService.findJewelryByNameContaining(name));
    }

    @GetMapping("/{category_id}")
    public ResponseEntity getJewelryByCategory(@PathVariable long category_id) {
        return ResponseEntity.ok(jewelryService.getJewelryByCategory(category_id));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity getJewelry(@PathVariable Long id) {
        return ResponseEntity.ok(jewelryService.getJewelry(id));
    }
    @GetMapping("/getJewelryISSOLD")
    public ResponseEntity getJewelryISSOLD() {
        return ResponseEntity.ok(jewelryService.getJewelryISSOLD());
    }
    @PutMapping("{id}")
    public ResponseEntity updateJewelry(@RequestBody JewelryRequest jewelry, @PathVariable Long id) {

        return ResponseEntity.ok(jewelryService.updateJewelry(jewelry, id));
    }
    @PutMapping("sendtoBuyer/{id}")
    public ResponseEntity sendtoBuyer( @PathVariable Long id) {

        return ResponseEntity.ok(jewelryService.sendtoBuyer(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable long id) {

        return ResponseEntity.ok(jewelryService.deleteJewelry(id));
    }

}
