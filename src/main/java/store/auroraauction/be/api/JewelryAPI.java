package store.auroraauction.be.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import store.auroraauction.be.entity.Jewelry;

import store.auroraauction.be.service.JewelryService;

@RestController
@RequestMapping("api/jewelry")
public class JewelryAPI
{
    @Autowired
    JewelryService jewelryService;
    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("test ok");
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Jewelry newjewelry) {
        Jewelry jewelry = jewelryService.addJewelry(newjewelry);
        return ResponseEntity.ok(jewelry);
    }
    @GetMapping("/Jewelrys")
    public ResponseEntity getJewelrys() {
        return ResponseEntity.ok(jewelryService.getJewelrys());
    }
    @GetMapping("/Jewelry/{id}")
    public ResponseEntity getJewelry(@PathVariable Long id) {

        return ResponseEntity.ok(jewelryService.getJewelry(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateJewelry(@RequestBody Jewelry jewelry, @PathVariable Long id) {

        return ResponseEntity.ok(jewelryService.updateJewelry(jewelry, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccount(@PathVariable long id) {

        return ResponseEntity.ok(jewelryService.deleteJewelry(id));
    }

}
