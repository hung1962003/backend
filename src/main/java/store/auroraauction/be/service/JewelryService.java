package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.JewelryRequest;
import store.auroraauction.be.Models.JewelryResponse;
import store.auroraauction.be.Models.RequestBuyRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.enums.StatusJewelryEnum;
import store.auroraauction.be.repository.CategoryRepository;
import store.auroraauction.be.repository.JewelryRepository;
import store.auroraauction.be.utils.AccountUtils;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JewelryService {
    @Autowired
    JewelryRepository jewelryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AccountUtils accountUtils;

    public Jewelry addJewelry(JewelryRequest newjewelry) {
        Jewelry jewelry = new Jewelry();
        Account account = accountUtils.getCurrentAccount();
        jewelry.setImages(newjewelry.getImage_url());

        jewelry.setDescription(newjewelry.getDescription());
        Category category = categoryRepository.findById(newjewelry.getCategory_id()).get();
        jewelry.setCategory(category);
        jewelry.setAccount(account);
        jewelry.setConditionReport(newjewelry.getConditionReport());
        jewelry.setName(newjewelry.getName());
        jewelry.setStatusJewelryEnum(StatusJewelryEnum.isInspected);
        jewelry.setHigh_estimated_price(newjewelry.getHigh_estimated_price());
        jewelry.setLow_estimated_price(newjewelry.getLow_estimated_price());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        jewelryRepository.save(jewelry);
        return jewelry;
    }
    public Jewelry createJewelry(){
        Jewelry jewelry = new Jewelry();
        Account account = accountUtils.getCurrentAccount();
        jewelry.setAccount(account);
        jewelry.setStatusJewelryEnum(StatusJewelryEnum.notReceived);
        return jewelryRepository.save(jewelry);
    }
    public Jewelry updateJewelry(JewelryRequest newjewelry, Long id) {
        Jewelry jewelry = jewelryRepository.findById(id).get();
        jewelry.setDescription(newjewelry.getDescription());
        jewelry.setImages(newjewelry.getImage_url());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        jewelry.setName(newjewelry.getName());
        jewelry.setHigh_estimated_price(newjewelry.getHigh_estimated_price());
        jewelry.setLow_estimated_price(newjewelry.getLow_estimated_price());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        return jewelryRepository.save(jewelry);
    }
    public List<Jewelry> findJewelryByNameContaining(String name){
        List<Jewelry> jewelryList = jewelryRepository.findJewelryByNameContaining(name);
        return jewelryList;
    }


//    public Jewelry updateJewelryRequest(RequestBuyRequest newjewelryRequest, Long id) {
//        Jewelry jewelry = jewelryRepository.findById(id).get();
//        jewelry.setDescription(newjewelryRequest.getDescription());
//        jewelry.setName(newjewelryRequest.getTitle());
//        jewelry.setStatusJewelryEnum(StatusJewelryEnum.notReceiced);
//        Set<Image> listImages = new HashSet<>();
//        for(String image_url:newjewelry.getImage_url()){
//            Image image = new Image();
//            image.setImage_url(image_url);
//            image.setJewelry(jewelry);
//            listImages.add(image);
//            jewelry.setImages(listImages);
//        }
//
//        jewelry.setImages(listImages);
//        return jewelryRepository.save(jewelry);
//    }
    public String deleteJewelry(Long id) {
        jewelryRepository.deleteById(id);
        return "Jewelry deleted";
    }

    public Jewelry getJewelry(long id) {
        System.out.println(id);
        return jewelryRepository.findById(id);
    }

    public List<Jewelry> getJewelrys() {
        List<Jewelry> jewelries = jewelryRepository.findAll();
        return jewelries;
    }


    public List<Jewelry> getJewelryByCategory(long categoryId) {
        List<Jewelry> jewelries = jewelryRepository.findJewelryByCategory_Id(categoryId);
        return jewelries;

    }

    public List<Jewelry> getJewelryReady() {

        List<Jewelry> jewelries = jewelryRepository.findJewelryByStatusJewelryEnum(StatusJewelryEnum.Ready);
        return jewelries;
    }

    public Jewelry setStatusReady(long id){
        Jewelry jewelry = jewelryRepository.findById(id);
        jewelry.setStatusJewelryEnum(StatusJewelryEnum.Ready);
        jewelryRepository.save(jewelry);
    return jewelry;
    }
    public Jewelry sendtoBuyer(long id){
        Jewelry jewelry = jewelryRepository.findById(id);
        jewelry.setStatusJewelryEnum(StatusJewelryEnum.isHandedover);
        jewelryRepository.save(jewelry);
        return jewelry;
    }
}
